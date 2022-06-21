package vn.teca.hddt.etax.tvan.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.util.SocketUtils;

@Component
@Slf4j
public class PortCustomizerConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Value("${port.range:30000,30999}")
    private Integer[] portRange;

    @Value("${grpc.range:31000,31999}")
    private Integer[] portRangeGrpc;

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        int port = SocketUtils.findAvailableTcpPort(portRange[0], portRange[1]);
        int grpcPort = SocketUtils.findAvailableTcpPort(portRangeGrpc[0], portRangeGrpc[1]);
        factory.setPort(port);
        System.setProperty("server.port", String.valueOf(port));
        System.setProperty("grpc.server.port", String.valueOf(grpcPort));
        log.info("Random Server Port is set to {} - {}.", port, grpcPort);
    }
}
