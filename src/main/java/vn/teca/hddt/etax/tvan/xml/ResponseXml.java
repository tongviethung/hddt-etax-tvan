package vn.teca.hddt.etax.tvan.xml;

import lombok.*;
import org.eclipse.persistence.oxm.annotations.XmlPath;
import vn.teca.hddt.etax.tvan.xml.adapter.DateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

@XmlRootElement(name = "DATA")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseXml {

    @XmlPath("HEADER/VERSION/text()")
    private String version;

    @XmlPath("HEADER/SENDER_CODE/text()")
    private String senderCode;

    @XmlPath("HEADER/SENDER_NAME/text()")
    private String senderName;

    @XmlPath("HEADER/RECEIVER_CODE/text()")
    private String receiverCode;

    @XmlPath("HEADER/RECEIVER_NAME/text()")
    private String receiverName;

    @XmlPath("HEADER/TRAN_CODE/text()")
    private String tranCode;

    @XmlPath("HEADER/MSG_ID/text()")
    private Integer msgId;

    @XmlPath("HEADER/MSG_REFID/text()")
    private String msgRefid;

    @XmlPath("HEADER/ID_LINK/text()")
    private String idLink;

    @XmlPath("HEADER/SEND_DATE/text()")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private Date sendDate;

    @XmlPath("HEADER/ORIGINAL_CODE/text()")
    private String originalCode;

    @XmlPath("HEADER/ORIGINAL_NAME/text()")
    private String originalName;

    @XmlPath("HEADER/ORIGINAL_DATE/text()")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private Date originalDate;

    @XmlPath("HEADER/ERROR_CODE/text()")
    private String errorCode;

    @XmlPath("HEADER/ERROR_DESC/text()")
    private String errorDesc;

    @XmlPath("HEADER/SPARE1/text()")
    private String spare1;

    @XmlPath("HEADER/SPARE2/text()")
    private String spare2;

    @XmlPath("HEADER/SPARE3/text()")
    private String spare3;

    @XmlPath("BODY/STATUS/MSG_STATUS/text()")
    private String msgStatus;

    @XmlPath("BODY/STATUS/MSG_STATUS_DESC/text()")
    private String msgStatusDesc;

    public void setMsgStatusDesc(String msgStatusDesc) {
        this.msgStatusDesc = msgStatusDesc
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("&", "&amp;");
    }
}
