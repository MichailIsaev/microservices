package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import service.domain.Visitor;

import java.io.IOException;
import java.net.URI;

@Service
public class VisitorServiceDelegate {

    private RestTemplate restTemplate;

    private DiscoveryClient discoveryClient;

    private ObjectMapper objectMapper;

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

    public Integer getAge(Integer id) throws RuntimeException, IOException {
        //TODO: discovery client approach
        /*String url = discoveryClient.
                getInstances("visitor-info-service").
                stream()
                .findAny()
                .orElseThrow(RuntimeException::new).getUri().toString();*/

        String response = restTemplate.exchange(
                "http://visitor-info-service/visitor/{id}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                },
                id
        ).getBody();

        return objectMapper.readTree(response).path("age").asInt();
    }
}
