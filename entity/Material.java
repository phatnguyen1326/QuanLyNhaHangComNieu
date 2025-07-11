package comnieu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Material {
    private String materialId;
    private String materialName;
    private String unit;
    private String status;       // TrangThai (e.g., "còn", "hết")
    private String supplierId;
}
