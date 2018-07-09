package service.delegate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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

    public Integer getAge(int id) throws NullPointerException {

        Integer age = null;

        try {
            String response = restTemplate.exchange(
                    "http://visitor-info-service/visitors/{id}",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<String>() {
                    },
                    id
            ).getBody();

            age = objectMapper.readTree(response).path("age").asInt();

        } catch (NullPointerException | IOException | HttpClientErrorException e) {
            System.err.println("Could`t parse response from Visitors service !");
        }
        return age;
    }
}
