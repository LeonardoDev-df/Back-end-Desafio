package br.com.history.service.dto.location;

import java.util.List;

public class LocationRequest {

    private List<ResultsDTO> results;

    public LocationRequest() {
    }

    public LocationRequest(List<ResultsDTO> results) {
        this.results = results;
    }

    public List<ResultsDTO> getResults() {
        return results;
    }

    public void setResults(List<ResultsDTO> results) {
        this.results = results;
    }
}
