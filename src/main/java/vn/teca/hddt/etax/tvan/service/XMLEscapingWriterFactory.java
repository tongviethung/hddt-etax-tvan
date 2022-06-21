package vn.teca.hddt.etax.tvan.service;

import org.apache.commons.lang3.StringEscapeUtils;
import org.codehaus.stax2.io.EscapingWriterFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class XMLEscapingWriterFactory implements EscapingWriterFactory {
    @Override
    public Writer createEscapingWriterFor(Writer writer, String s){
        return new Writer(){
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                String val = "";
                for (int i = off; i < len; i++) {
                    val += cbuf[i];
                }
                String escapedStr = StringEscapeUtils.escapeXml(val);
                writer.write(escapedStr);
            }

            @Override
            public void flush() throws IOException {
                writer.flush();
            }

            @Override
            public void close() throws IOException {
                writer.close();
            }
        };
    }

    @Override
    public Writer createEscapingWriterFor(OutputStream outputStream, String s){
        return null;
    }
}