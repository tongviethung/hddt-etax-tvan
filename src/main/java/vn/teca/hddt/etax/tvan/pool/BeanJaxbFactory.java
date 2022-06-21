package vn.teca.hddt.etax.tvan.pool;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import vn.teca.hddt.etax.tvan.xml.ResponseXml;
import vn.teca.hddt.etax.tvan.xml.TKhaiDLXml;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Service
public class BeanJaxbFactory {

    // region UNMARSHALLER

    @Bean
    public ObjectPool<Unmarshaller> unmarshallerTKhaiDLXml() {
        return new GenericObjectPool<>(new UnmarshallerPoolFactory(TKhaiDLXml.class));
    }

    // endregion


    // region MARSHALLER

    @Bean
    public ObjectPool<Marshaller> marshallerTKhaiDLXml() {
        return new GenericObjectPool<>(new MarshallerPoolFactory(TKhaiDLXml.class));
    }

    @Bean
    public ObjectPool<Marshaller> marshallerResponseXml() {
        return new GenericObjectPool<>(new MarshallerPoolFactory(ResponseXml.class));
    }

    // endregion
}
