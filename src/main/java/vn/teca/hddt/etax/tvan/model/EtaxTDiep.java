package vn.teca.hddt.etax.tvan.model;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.NamingStrategy;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.teca.hddt.etax.tvan.model.enums.EtaxTTXLy;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(defaultKeyspace = "ks_hoadon")
@NamingStrategy(convention = NamingConvention.CASE_INSENSITIVE)
@CqlName("hddt_etax_tdiep")
public class EtaxTDiep {
    @PartitionKey
    private String msgid;

    private String mngui;

    private String mnnhan;

    private String msgrefid;

    private UUID hsgoc;

    private EtaxTTXLy ttxly;

    private Instant ncnhat;

    private Instant ntao;
}
