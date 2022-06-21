package vn.teca.hddt.etax.tvan.model;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.teca.hddt.etax.tvan.model.enums.LKTThue;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(defaultKeyspace = "ks_hoadon")
@NamingStrategy(convention = NamingConvention.CASE_INSENSITIVE)
@CqlName("hddt_etax_tkdlctiet")
public class TKhaiCTiet {
    @PartitionKey(1)
    private String mst;
    @PartitionKey(2)
    private LKTThue lktthue;
    @PartitionKey(3)
    private String ktthue;
    @PartitionKey(4)
    private Integer stkhai;
    @ClusteringColumn(1)
    private String tsuatgoc;
    @ClusteringColumn(2)
    private UUID id;

    private Integer stt;

    private String khmshdon;

    private String khhdon;

    private String shdon;

    private Instant nlap;

    private String tnmua;

    private String mstnmua;

    private Double dtcthue;

    private Double tgtgt;

    private String gchu;

    private Double tsuat;

    private String ltsuat;

    private Double tgdtcthue; // cột này sẽ có giá trị giống nhau ở tất cả hóa đơn cùng tờ khai

    private Double tgtgtgt; // cột này sẽ có giá trị giống nhau ở tất cả hóa đơn cùng tờ khai

    private Instant ntao; // bổ sung thêm

    private String mcqtqly;

    private String msgid;
}
