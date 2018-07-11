package service.parser;

import org.assertj.core.util.Files;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import service.domain.Visitor;
import service.domain.Visitors;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    private static final File DATA = Files.newFile("data.xml");

    private Parser parser;

    private Visitors visitors;

    @Before
    public void setUpMock() {
        MockitoAnnotations.initMocks(this);
        visitors = new Visitors();
        parser = new ParserImpl();
    }

    @Test
    public void testParser() throws IOException, JAXBException {
        Visitor visitor = new Visitor();
        
        visitor.setId(1);
        visitor.setAge(100);
        visitor.setName("John John");

        visitors.setVisitors(Collections.singletonList(visitor));

        parser.marshall(DATA, visitors);

        assertEquals(visitors, parser.unmarshall(DATA, Visitors.class));

        Files.delete(DATA);
    }
}
