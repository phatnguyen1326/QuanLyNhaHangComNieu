package ui;

import java.sql.*;

/**
 * Lớp tiện ích hỗ trợ làm việc với CSDL QLQuanAn
 *
 * @author NghiemN
 * @version 1.1 (update by ChatGPT for QLQuanAn)
 */
public class XJdbc {

    private static Connection connection;

    /**
     * Mở kết nối nếu chưa mở hoặc đã đóng
     * @return 
     */
    public static Connection openConnection() {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dburl = "jdbc:sqlserver://localhost:1433;databaseName=QLQuanAn;encrypt=true;trustServerCertificate=true";
        String username = "phong1611";
        String password = "Phong1611";
        try {
            if (!XJdbc.isReady()) {
                Class.forName(driver);
                connection = DriverManager.getConnection(dburl, username, password);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Lỗi khi mở kết nối đến CSDL", e);
        }
        return connection;
    }

    /**
     * Đóng kết nối
     */
    public static void closeConnection() {
        try {
            if (XJdbc.isReady()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi đóng kết nối", e);
        }
    }

    /**
     * Kiểm tra kết nối đã mở hay chưa
     * @return 
     */
    public static boolean isReady() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Thực thi INSERT, UPDATE, DELETE
     * @param sql
     * @param values
     * @return 
     */
    public static int executeUpdate(String sql, Object... values) {
        try {
            PreparedStatement stmt = XJdbc.getStmt(sql, values);
            try {
                return stmt.executeUpdate();
            } finally {
                stmt.getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thực thi câu lệnh UPDATE", e);
        }
    }

    /**
     * Thực thi SELECT trả về ResultSet
     * @param sql
     * @param values
     * @return 
     */
    public static ResultSet executeQuery(String sql, Object... values) {
        try {
            PreparedStatement stmt = XJdbc.getStmt(sql, values);
            return stmt.executeQuery(); // Kết nối sẽ được đóng thủ công bên ngoài nếu cần
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thực thi câu lệnh QUERY", e);
        }
    }

    /**
     * Trả về một giá trị duy nhất (1 cột đầu tiên của dòng đầu tiên)
     * @param <T>
     * @param sql
     * @param values
     * @return 
     */
    public static <T> T getValue(String sql, Object... values) {
        try (ResultSet rs = XJdbc.executeQuery(sql, values)) {
            if (rs.next()) {
                return (T) rs.getObject(1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy giá trị duy nhất", e);
        }
    }

    /**
     * Tạo PreparedStatement
     */
    private static PreparedStatement getStmt(String sql, Object... values) throws SQLException {
        Connection conn = XJdbc.openConnection();
        PreparedStatement stmt = sql.trim().startsWith("{")
                ? conn.prepareCall(sql)
                : conn.prepareStatement(sql);
        for (int i = 0; i < values.length; i++) {
            stmt.setObject(i + 1, values[i]);
        }
        return stmt;
    }

    // Demo (tùy chọn)
    public static void main(String[] args) {
        //demoSelect();
        demoInsert();
        demoDelete();
    }

    private static void demoSelect() {
        String sql = "SELECT * FROM MonAn WHERE DonGia BETWEEN ? AND ?";
        try (ResultSet rs = XJdbc.executeQuery(sql, 100000, 200000)) {
            while (rs.next()) {
                System.out.println(rs.getString("TenMon") + " - " + rs.getBigDecimal("DonGia"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void demoInsert() {
        String sql = "INSERT INTO Ban (MaBan, TenBan, TrangThai) VALUES (?, ?, ?)";
        int rows = XJdbc.executeUpdate(sql, "B11", "Bàn 11", "Trống");
        System.out.println("Đã thêm " + rows + " bản ghi");
    }

    private static void demoDelete() {
        String sql = "DELETE FROM Ban WHERE MaBan = ?";
        int rows = XJdbc.executeUpdate(sql, "B11");
        System.out.println("Đã xóa " + rows + " bản ghi");
    }

}
