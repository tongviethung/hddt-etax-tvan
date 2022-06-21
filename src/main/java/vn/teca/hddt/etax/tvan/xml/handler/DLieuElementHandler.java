package vn.teca.hddt.etax.tvan.xml.handler;

import lombok.extern.slf4j.Slf4j;
import vn.teca.hddt.etax.tvan.exception.JAXBConversionException;

import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.DomHandler;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

@Slf4j
public class DLieuElementHandler implements DomHandler<String, StreamResult> {
    private static final String DLIEU_START_TAG = "<DLieu>";
    private static final String DLIEU_END_TAG = "</DLieu>";

    @Override
    public StreamResult createUnmarshaller(ValidationEventHandler errorHandler) {
        return new StreamResult(new StringWriter());
    }

    @Override
    public String getElement(StreamResult rt) {
        String xml = rt.getWriter().toString();
        int beginIndex = xml.indexOf(DLIEU_START_TAG) + DLIEU_START_TAG.length();
        int endIndex = xml.lastIndexOf(DLIEU_END_TAG);
        if (endIndex - beginIndex < 0 || beginIndex < 0 || endIndex < 0) {
            return null;
        }
        return xml.substring(beginIndex, endIndex);
    }

    @Override
    public Source marshal(String n, ValidationEventHandler errorHandler) {
        try {
            String xml = DLIEU_START_TAG + n.trim() + DLIEU_END_TAG;
            StringReader xmlReader = new StringReader(xml);
            return new StreamSource(xmlReader);
        } catch (Exception e) {
            throw new JAXBConversionException(e);
        }
    }
}
