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

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(defaultKeyspace = "ks_hosogoc")
@NamingStrategy(convention = NamingConvention.CASE_INSENSITIVE)
@CqlName("hddt_hsg_etax_tdiep")
public class HSGEtaxTDiep {
    @PartitionKey
    private UUID id;
    private String hsgoc;
    private Instant ntao;
}