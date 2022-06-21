package vn.teca.hddt.etax.tvan.service;

import org.codehaus.stax2.XMLOutputFactory2;
import org.springframework.stereotype.Service;

import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class XmlSplitter {
    private final XMLOutputFactory factory = XMLOutputFactory.newInstance();
    private final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

    public void splitByElement(String xml, String elementName, Consumer<String> consume) throws XMLStreamException, IOException {
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new StringReader(xml));
        factory.setProperty(XMLOutputFactory2.P_TEXT_ESCAPER, new XMLEscapingWriterFactory());
        while (xmlEventReader.hasNext()) {
            XMLEvent event = xmlEventReader.nextEvent();
            if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(elementName)) {
                try (StringWriter xmlWriter = new StringWriter()) {
                    XMLEventWriter xmlEventWriter = factory.createXMLEventWriter(xmlWriter);
                    while (!(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(elementName))) {
                        xmlEventWriter.add(event);
                        event = xmlEventReader.nextEvent();

                        if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(elementName)) {
                            xmlEventWriter.add(event);
                        }
                    }
                    xmlEventWriter.close();
                    consume.accept(xmlWriter.toString());
                }
            }
        }
    }

    public List<String> splitByElement(String xml, String elementName) throws XMLStreamException, IOException {
        List<String> xmls = new ArrayList<>();
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new StringReader(xml));
        factory.setProperty(XMLOutputFactory2.P_TEXT_ESCAPER, new XMLEscapingWriterFactory());
        while (xmlEventReader.hasNext()) {
            XMLEvent event = xmlEventReader.nextEvent();
            if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(elementName)) {
                try (StringWriter xmlWriter = new StringWriter()) {
                    XMLEventWriter xmlEventWriter = factory.createXMLEventWriter(xmlWriter);
                    while (!(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(elementName))) {
                        xmlEventWriter.add(event);
                        event = xmlEventReader.nextEvent();

                        if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(elementName)) {
                            xmlEventWriter.add(event);
                        }
                    }
                    xmlEventWriter.close();
                    xmls.add(xmlWriter.toString());
                }
            }
        }
        return xmls;
    }
}

