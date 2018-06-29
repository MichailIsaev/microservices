package service.configuration;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import service.domain.Visitors;
import service.parser.Parser;
import service.parser.ParserImpl;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Configuration
@ComponentScan(basePackages = {"service"})
@PropertySource(value = "classpath:data.properties", ignoreResourceNotFound = true)
public class DataConfig {

    @Value("${data.path}")
    private  String pathURL;

    @Bean
    public Parser parser() {
        return new ParserImpl();
    }

    @Bean("visitors")
    public Visitors loadVisitors() throws BeanCreationException, IOException {
        Visitors visitors = null;
        try {
            visitors = (Visitors) parser().unmarshall(new File(pathURL), Visitors.class);
        } catch (JAXBException e) {
            //TODO: prepare
            e.printStackTrace();
        }
        return Optional.ofNullable(visitors).orElseThrow(() -> new BeanCreationException("Visitors did`t load."));
    }
}
