package vn.teca.hddt.etax.tvan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableJms
@Service
@Slf4j
@RequiredArgsConstructor
public class EsbConsumer {

    private final TKhaiDLService tkhaiDLService;

    @Autowired
    private DocTaxService docTaxService;

//    @JmsListener(destination = "${hddt.ibm.etax.outbox}")
    public void listener(Message<String> message) {
        log.info("Message is captured: " + message.getHeaders());
//        tkhaiDLService.process(message.getPayload());
        docTaxService.process(message.getPayload());
    }
}
