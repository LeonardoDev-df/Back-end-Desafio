package br.com.history.service.dto.location;

public class ResultsDTO {

    private GeometryDTO geometry;

    public ResultsDTO() {
    }

    public ResultsDTO(GeometryDTO geometry) {
        this.geometry = geometry;
    }

    public GeometryDTO getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryDTO geometry) {
        this.geometry = geometry;
    }
}
