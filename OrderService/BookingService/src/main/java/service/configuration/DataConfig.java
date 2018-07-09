package service.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.web.client.RestTemplate;
import service.persistence.BookingMapper;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@Configuration
@ComponentScan("service")
@MapperScan("service.persistence")
public class DataConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.
                create().
                url("jdbc:postgresql://localhost:5432/ticket_booking").
                driverClassName(org.postgresql.Driver.class.getName()).
                username("postgres").
                password("12345").
                build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.getObject().getConfiguration().addMapper(BookingMapper.class);
        return (SqlSessionFactory) sessionFactory.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @PostConstruct
    public void createDBContent() {
        Resource resource = new ClassPathResource("data-postgresql.sql");
        try {
            new ResourceDatabasePopulator(resource).execute(dataSource());
        } catch (ScriptException e) {
            System.out.println("SQL exception in the initial script file !");
        }
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
