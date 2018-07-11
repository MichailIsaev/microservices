package service.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class VisitorServiceDelegate {

    private RestTemplate restTemplate;

    private DiscoveryClient discoveryClient;

    private ObjectMapper objectMapper;

    private ReentrantLock lock = new ReentrantLock();

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

    public Callable<Integer> getAge(int id) throws NullPointerException {

        Callable<Integer> callableTask = null;

        try {

            lock.lock();

            callableTask = () -> {

                String response = restTemplate.exchange(
                        "http://visitor-info-service/visitors/{id}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<String>() {
                        },
                        id
                ).getBody();

                TimeUnit.SECONDS.sleep(1);

                return objectMapper.readTree(response).path("age").asInt();
            };

        } catch (NullPointerException | HttpClientErrorException e) {
            System.err.println("Could`t parse response from Visitors service !");
        } finally {
            lock.unlock();
        }

        return callableTask;
    }
}
