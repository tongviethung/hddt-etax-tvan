package vn.teca.hddt.etax.tvan.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EsbProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${hddt.ibm.etax.inbox:etax}")
    private String defaultQueue;

    @Retryable(
            maxAttempts = 5,
            backoff = @Backoff(delay = 3000, multiplier = 2),
            value = {JmsException.class}
    )
    public void send(String queueName, String payload) {
        if (queueName == null) {
            queueName = defaultQueue;
        }
        log.info("Payload: {}", payload);
        jmsTemplate.convertAndSend(queueName, payload);
    }

    public void send(String payload) {
        send(null, payload);
    }
}
