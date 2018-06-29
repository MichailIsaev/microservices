package service.parser;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class ParserImpl implements Parser {
    @Override
    public void marshall(File file, Object object) throws JAXBException {
        Marshaller marshaller = JAXBContext.newInstance(object.getClass()).createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(object, file);
    }

    @Override
    public Object unmarshall(File file, Class c) throws JAXBException {
        return JAXBContext
                .newInstance(c)
                .createUnmarshaller()
                .unmarshal(file);
    }
}
