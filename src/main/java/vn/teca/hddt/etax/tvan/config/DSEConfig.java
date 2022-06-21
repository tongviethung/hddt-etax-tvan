package vn.teca.hddt.etax.tvan.config;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import vn.teca.hddt.etax.tvan.codec.EtaxTTXLyCodec;
import vn.teca.hddt.etax.tvan.codec.LKTThueCodec;
import vn.teca.hddt.etax.tvan.repository.InventoryMapper;
import vn.teca.hddt.etax.tvan.repository.InventoryMapperBuilder;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Lazy
public class DSEConfig {
    @Value("#{'${spring.data.cassandra.contact-points}'.split(',')}")
    private List<String> hosts;

    @Value("${spring.data.cassandra.username}")
    private String username;

    @Value("${spring.data.cassandra.password}")
    private String password;

    public CqlSessionBuilder cqlSessionDefault() {
        List<InetSocketAddress> contactPoints = hosts.parallelStream().map(host -> host.split(":"))
                .map(host -> new InetSocketAddress(host[0], Integer.parseInt(host[1])))
                .collect(Collectors.toList());

        DriverConfigLoader loader = DriverConfigLoader.programmaticBuilder()
                .withString(DefaultDriverOption.REQUEST_CONSISTENCY, DefaultConsistencyLevel.LOCAL_ONE.name())
                .withString(DefaultDriverOption.REQUEST_SERIAL_CONSISTENCY, DefaultConsistencyLevel.LOCAL_SERIAL.name())
                .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(0))
                .withInt(DefaultDriverOption.CONNECTION_MAX_REQUESTS, 32768)
                .withBoolean(DefaultDriverOption.METADATA_SCHEMA_ENABLED, true)
                .build();

        return CqlSession.builder()
                .addContactPoints(contactPoints)
                .withAuthCredentials(username, password)
                .addTypeCodecs(
                        new LKTThueCodec(),
                        new EtaxTTXLyCodec()
                )
                .withConfigLoader(loader);
    }

    @Bean("DC_Trans_Search")
    @Primary
    public InventoryMapper transSearchInventoryMapper() throws Exception {
        return new InventoryMapperBuilder(cqlSessionDefault()
                .withLocalDatacenter("DC1")
                .build()).build();
    }

    @Bean("DC_Original_Docs")
    public InventoryMapper originalInventoryMapper() throws Exception {
        return new InventoryMapperBuilder(cqlSessionDefault()
                .withLocalDatacenter("DC1")
                .build()).build();
    }

    @Bean("DC_Analytics")
    public InventoryMapper analyticInventoryMapper() throws Exception {
        return new InventoryMapperBuilder(cqlSessionDefault()
                .withLocalDatacenter("DC_Analytics")
                .build()).build();
    }
}
