package comnieu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Supplier {
    private String maNCC;
    private String tenNCC;
    private String diaChi;
    private String dienThoai;

    @Override
    public String toString() {
        return maNCC + " - " + tenNCC;
    }
}
