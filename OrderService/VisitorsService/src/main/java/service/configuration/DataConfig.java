package service.configuration;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import service.domain.Visitors;
import service.parser.Parser;
import service.parser.ParserImpl;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Configuration
@ComponentScan(basePackages = {"service"})
public class DataConfig {

    @Bean
    public Parser parser() {
        return new ParserImpl();
    }

    @Bean
    public Visitors loadVisitors() throws BeanCreationException, IOException {
        Visitors visitors = null;
        try {
            visitors = (Visitors) parser().
                    unmarshall(
                            new ClassPathResource("visitors.xml").
                                    getFile(),
                            Visitors.class
                    );
        } catch (JAXBException e) {
            System.err.println("Let`s check your .xml data file !");
        }
        return visitors;
    }
}
