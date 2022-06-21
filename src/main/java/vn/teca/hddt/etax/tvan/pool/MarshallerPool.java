package vn.teca.hddt.etax.tvan.pool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.ObjectPool;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.oxm.CharacterEscapeHandler;
import vn.teca.hddt.etax.tvan.exception.JAXBValidationException;
import vn.teca.hddt.etax.tvan.model.enums.ErrorCode;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Slf4j
public class MarshallerPool {
    private final ObjectPool<Marshaller> pool;

    public MarshallerPool(ObjectPool<Marshaller> pool) {
        this.pool = pool;
    }

    public <T> String objectToXml(T object, Class<T> clazz) throws JAXBValidationException {
        return objectToXml(object, clazz, false);
    }

    public <T> String objectToXml(T object, Class<T> clazz, boolean appendHeader)
            throws JAXBValidationException {
        Marshaller marshaller = null;
        try {
            marshaller = pool.borrowObject();
            StringWriter sw = new StringWriter();
            marshaller.setProperty(MarshallerProperties.CHARACTER_ESCAPE_HANDLER,
                    (CharacterEscapeHandler) (buffer, start, length, isAttributeValue, out) ->
                            out.write(buffer, start, length));
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
            if (!appendHeader) {
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            }
            marshaller.marshal(object, sw);
            return sw.toString();
        } catch (Exception e) {
            log.error("Lỗi không lấy được marhaller", e);
            throw new JAXBValidationException(e.getMessage(), ErrorCode.ERR_80000);
        } finally {
            try {
                if (null != marshaller) {
                    pool.returnObject(marshaller);
                }
            } catch (Exception e) {
                // ignored
            }
        }
    }
}
