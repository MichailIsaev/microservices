package service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.omg.CORBA.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryProperties;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import service.configuration.DataConfig;
import service.controllers.PricingController;
import service.delegate.VisitorServiceDelegate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

//@RunWith(SpringRunner.class)
/*@ContextConfiguration(
        classes = {PricingServiceApplication.class, DataConfig.class},
        loader = AnnotationConfigContextLoader.class)*/
public class VisitorServiceDelegateTest {

    private static final int VISITORS_SERVICE_PORT = 8081;

    private static final String VISITORS_DC_HOST = "visitor-info-service";

   /* @Autowired
    private VisitorServiceDelegate visitorServiceDelegate;
    */

    private WireMockServer wireMockServer = new WireMockServer();


   // private DiscoveryClient discoveryClient;

    @Before
    public void setUp() {
        //wireMockServer.start();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {
        int consumerId = 1;
        //configureFor(VISITORS_DC_HOST, VISITORS_SERVICE_PORT);
//        stubFor(get(urlEqualTo(String.format("/visitors/%s", consumerId))).willReturn(aResponse().withStatus(200)));
        //visitorServiceDelegate.getAge(consumerId);
        //wireMockServer.stop();
    }
}
