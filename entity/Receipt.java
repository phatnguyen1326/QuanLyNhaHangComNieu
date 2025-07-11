package comnieu.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Receipt {
    private String receiptId;   // MaPN
    private String materialId;  // MaNL
    private Integer quantity;   // SoLuong
    private BigDecimal price;   // DonGia
}
