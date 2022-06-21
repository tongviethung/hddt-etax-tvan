package vn.teca.hddt.etax.tvan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRetry
// @EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class EtaxTvanApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtaxTvanApplication.class, args);
    }

}
