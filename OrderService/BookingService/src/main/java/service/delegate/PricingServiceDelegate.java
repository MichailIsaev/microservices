package service.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

@Component
public class PricingServiceDelegate {

    private RestTemplate restTemplate;

    private DiscoveryClient discoveryClient;

    private ObjectMapper objectMapper;

    private ReentrantLock reentrantLock = new ReentrantLock();

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public Double getPrice(int visitorId, LocalDateTime seanceTime) throws NullPointerException, IOException {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("http://pricing-service/price").
                queryParam("seance_time", seanceTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))).
                queryParam("visitor_id", visitorId);

        String response = null;

        reentrantLock.lock();

        try {
            response = restTemplate.exchange(
                    uriComponentsBuilder.toUriString().replace("%20", " "),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<String>() {
                    }
            ).getBody();
        } catch (HttpClientErrorException e) {
            System.err.println("Could`t attempt response data from Price service");
        } finally {
            reentrantLock.unlock();
        }

        return objectMapper.readTree(response).path("price").asDouble();
    }
}
