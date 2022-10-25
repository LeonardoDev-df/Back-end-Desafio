package br.com.history.client;

import br.com.history.service.dto.location.LocationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationClient {

    public LocationRequest findLocation(String endereco) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(
            "https://maps.googleapis.com/maps/api/geocode/json?address=" + endereco +
                "api_key", LocationRequest.class);
    }
}
