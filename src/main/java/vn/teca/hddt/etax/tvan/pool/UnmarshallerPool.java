package vn.teca.hddt.etax.tvan.pool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.ObjectPool;
import org.eclipse.persistence.exceptions.BeanValidationException;
import org.eclipse.persistence.exceptions.XMLMarshalException;
import vn.teca.hddt.etax.tvan.exception.JAXBValidationException;
import vn.teca.hddt.etax.tvan.util.JAXBXPath;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.util.ValidationEventCollector;
import java.io.StringReader;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class UnmarshallerPool {
    private final ObjectPool<Unmarshaller> pool;

    public UnmarshallerPool(ObjectPool<Unmarshaller> pool) {
        this.pool = pool;
    }

    public <T> T xmlToObject(String xmlString, Class<T> clazz) throws JAXBValidationException {
        ValidationEventCollector eventCollector = new ValidationEventCollector();
        String handlerError = "";
        Unmarshaller unmarshaller = null;
        try {
            unmarshaller = pool.borrowObject();
            unmarshaller.setEventHandler(eventCollector);
            T obj = clazz.cast(unmarshaller.unmarshal(new StringReader(xmlString)));
            handlerError = Arrays.stream(eventCollector.getEvents())
                    .map(ValidationEvent::getMessage)
                    .filter(message -> !message.contains("unexpected element"))
                    .map(message -> {
                        if (!message.contains("Exception Description")) {
                            return message.replace("Unparseable date", "Lỗi chuyển đổi dữ liệu sang định dạng ngày");
                        } else {
                            return "Lỗi chuyển đổi dữ liệu sang định dạng số: " + message.substring(message.indexOf('[') + 1, message.indexOf(']'));
                        }
                    })
                    .collect(Collectors.joining("\n"));
            if (StringUtils.isBlank(handlerError)) {
                return obj;
            } else {
                throw new JAXBException("handler");
            }
        } catch (JAXBException ex) {
            log.error("Error parse");
            //log.error(ex.getMessage(), ex);
            if (!ex.getMessage().equals("handler")) {
                handlerError = Arrays.stream(eventCollector.getEvents())
                        .map(ValidationEvent::getMessage)
                        .filter(message -> !message.contains("unexpected element"))
                        .map(message -> {
                            if (!message.contains("Exception Description")) {
                                return message.replace("Unparseable date", "Lỗi chuyển đổi dữ liệu sang định dạng ngày");
                            } else {
                                return "Lỗi chuyển đổi dữ liệu sang định dạng số: " + message.substring(message.indexOf('[') + 1, message.indexOf(']'));
                            }
                        })
                        .collect(Collectors.joining("\n"));
                if (ex.getLinkedException() instanceof BeanValidationException) {
                    BeanValidationException e = (BeanValidationException) ex.getLinkedException();
                    if (StringUtils.isBlank(handlerError)) {
                        throw new JAXBValidationException(new JAXBXPath().getXPathFromErr(e.getInternalException().getMessage(), clazz));
                    } else {
                        throw new JAXBValidationException(handlerError + "\n" + new JAXBXPath().getXPathFromErr(e.getInternalException().getMessage(), clazz));
                    }
                }
            } else {
                throw new JAXBValidationException(handlerError);
            }
            if (ex.getLinkedException() instanceof XMLMarshalException) {
                throw new JAXBValidationException(((XMLMarshalException) ex.getLinkedException()).getInternalException().getMessage());
            }
            throw new JAXBValidationException(ex.getMessage());
        } catch (Exception e) {
            log.error("Lỗi không lấy được unmarhaller", e);
            throw new JAXBValidationException(e.getMessage());
        } finally {
            try {
                if (null != unmarshaller) {
                    pool.returnObject(unmarshaller);
                }
            } catch (Exception e) {
                // ignored
            }
        }
    }

}
