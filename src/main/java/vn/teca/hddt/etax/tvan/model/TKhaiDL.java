package vn.teca.hddt.etax.tvan.model;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.teca.hddt.etax.tvan.model.enums.LKTThue;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(defaultKeyspace = "ks_hoadon")
@NamingStrategy(convention = NamingConvention.CASE_INSENSITIVE)
@CqlName("hddt_etax_tkdlieu")
public class TKhaiDL {
    @PartitionKey(1)
    private String mst;
    @PartitionKey(2)
    private LKTThue lktthue;
    @PartitionKey(3)
    private String ktthue;
    @PartitionKey(4)
    private Integer stkhai;
    private String pban;
    private String mso;
    private String ten;
    private String ddanh;
    private Instant nlap;
    private String tnnt;
    private String tdlthue;
    private String mstdlthue;
    private String dvtte;
    private Double tgdthu;
    private Double tgthue;
    private Instant ntao; // bổ sung thêm
    private UUID hsgoc;
    private String mcqtqly;
    private String msgid;
    @Transient
    private List<TKhaiCTiet> tkdlctiets;
    @Transient
    private Map<String, List<TKhaiCTiet>> tkdlctietmap;
}
