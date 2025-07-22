package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ui.Employee;
import ui.XJdbc;

public class EmployeeDAOImpl implements EmployeeDAO {

    private final String createSql = "INSERT INTO NhanVien (MaNV, HoTen, GioiTinh, NgaySinh, DienThoai, [User], [Pass], ChucVu) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String updateSql = "UPDATE NhanVien SET HoTen=?, GioiTinh=?, NgaySinh=?, DienThoai=?, [User]=?, [Pass]=?, ChucVu=? WHERE MaNV=?";
    private final String deleteSql = "DELETE FROM NhanVien WHERE MaNV=?";
    private final String findAllSql = "SELECT * FROM NhanVien";
    private final String findByIdSql = "SELECT * FROM NhanVien WHERE MaNV=?";

    @Override
    public Employee create(Employee entity) {
        try (Connection con = XJdbc.openConnection();
             PreparedStatement ps = con.prepareStatement(createSql)) {

            ps.setString(1, entity.getMaNV());
            ps.setString(2, entity.getHoTen());
            ps.setString(3, entity.getGioiTinh());
            ps.setDate(4, new java.sql.Date(entity.getNgaySinh().getTime()));
            ps.setString(5, entity.getDienThoai());
            ps.setString(6, entity.getUser());
            ps.setString(7, entity.getPass());
            ps.setString(8, entity.getChucVu());

            ps.executeUpdate();
            return entity;

        } catch (SQLException e) {
            throw new RuntimeException("❌ Lỗi khi thêm Nhân viên", e);
        }
    }

    @Override
    public void update(Employee entity) {
        try (Connection con = XJdbc.openConnection();
             PreparedStatement ps = con.prepareStatement(updateSql)) {

            ps.setString(1, entity.getHoTen());
            ps.setString(2, entity.getGioiTinh());
            ps.setDate(3, new java.sql.Date(entity.getNgaySinh().getTime()));
            ps.setString(4, entity.getDienThoai());
            ps.setString(5, entity.getUser());
            ps.setString(6, entity.getPass());
            ps.setString(7, entity.getChucVu());
            ps.setString(8, entity.getMaNV());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("❌ Lỗi khi cập nhật Nhân viên", e);
        }
    }

    @Override
    public void deleteById(String id) {
        try (Connection con = XJdbc.openConnection();
             PreparedStatement ps = con.prepareStatement(deleteSql)) {

            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("❌ Lỗi khi xóa Nhân viên", e);
        }
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> list = new ArrayList<>();
        try (Connection con = XJdbc.openConnection();
             PreparedStatement ps = con.prepareStatement(findAllSql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("❌ Lỗi khi lấy danh sách Nhân viên", e);
        }
        return list;
    }

    @Override
    public Employee findById(String id) {
        try (Connection con = XJdbc.openConnection();
             PreparedStatement ps = con.prepareStatement(findByIdSql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("❌ Lỗi khi tìm Nhân viên theo ID", e);
        }
        return null;
    }

    private Employee mapRow(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setMaNV(rs.getString("MaNV"));
        e.setHoTen(rs.getString("HoTen"));
        e.setGioiTinh(rs.getString("GioiTinh"));
        e.setNgaySinh(rs.getDate("NgaySinh"));
        e.setDienThoai(rs.getString("DienThoai"));
        e.setUser(rs.getString("User"));
        e.setPass(rs.getString("Pass"));
        e.setChucVu(rs.getString("ChucVu"));
        return e;
    }
}
