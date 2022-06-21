package vn.teca.hddt.etax.tvan.model;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(defaultKeyspace = "ks_hoadon")
@NamingStrategy(convention = NamingConvention.CASE_INSENSITIVE)
@CqlName("hddt_timelog")
public class TimeLog {
    @PartitionKey
    private String mtdiep;
    @ClusteringColumn
    private UUID id;
    private String ttac;
    private Instant ntao;
    private String mtdtchieu;
    private String tvan;
}
