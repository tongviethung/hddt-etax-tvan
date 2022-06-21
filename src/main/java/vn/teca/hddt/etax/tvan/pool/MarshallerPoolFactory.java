package vn.teca.hddt.etax.tvan.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.eclipse.persistence.jaxb.JAXBContextFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class MarshallerPoolFactory extends BasePooledObjectFactory<Marshaller> {

    private final Class<?> clazz;

    public MarshallerPoolFactory(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Marshaller create() throws Exception {
        JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[]{this.clazz}, null);
        return jaxbContext.createMarshaller();
    }

    @Override
    public PooledObject<Marshaller> wrap(Marshaller marshaller) {
        return new DefaultPooledObject<>(marshaller);
    }

}
