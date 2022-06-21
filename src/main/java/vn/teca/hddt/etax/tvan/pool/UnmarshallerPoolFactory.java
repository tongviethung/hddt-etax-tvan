package vn.teca.hddt.etax.tvan.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.eclipse.persistence.jaxb.JAXBContextFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class UnmarshallerPoolFactory extends BasePooledObjectFactory<Unmarshaller> {

    private final Class<?> clazz;

    public UnmarshallerPoolFactory(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Unmarshaller create() throws Exception {
        JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[]{this.clazz}, null);
        return jaxbContext.createUnmarshaller();
    }

    @Override
    public PooledObject<Unmarshaller> wrap(Unmarshaller unmarshaller) {
        return new DefaultPooledObject<>(unmarshaller);
    }

}
