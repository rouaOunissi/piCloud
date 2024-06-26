package com.pi.ressources.ServicesImpl;

import com.pi.ressources.Services.SynonymService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pi.ressources.entities.SynonymResponse;
import java.io.IOException;

@Service
public class SynonymServiceImpl implements SynonymService {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private ObjectMapper objectMapper;


    public SynonymServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("zGXoRIglJM7MJLwBrkttKA==l1eA5pXqSHkZheuv")
    private String apiKey;

    @Override
    public String[] getSynonyms(String word) {
        String apiUrl = "https://api.api-ninjas.com/v1/thesaurus?word=" + word;
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        try {

            SynonymResponse synonymResponse = objectMapper.readValue(response.getBody(), SynonymResponse.class);

            return synonymResponse.getSynonyms();
        } catch (IOException e) {
            e.printStackTrace();

            return new String[0];
        }
    }
}
