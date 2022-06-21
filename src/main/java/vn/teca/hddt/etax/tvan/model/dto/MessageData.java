package vn.teca.hddt.etax.tvan.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageData {
    private String messageCode;
    private short messageType;
    private String xmlData;
    private String originalMessageId;
    private byte sentMethod;
    private String commonTin;
    private Integer commonQuantity;
    private String tvanTin;
    private String tvanName; // using to identify out topic
    private Date receivedTime;
    private boolean guest;
    private String tempId;
    private String senderUser; // portal user
}
