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
public class ReceiptDetail {
    private String receiptDetailId;  // MaCTPN
    private String receiptId;        // MaPN
    private String materialId;       // MaNL
    private Float quantity;          // SL
    private BigDecimal price;        // DonGia
}
