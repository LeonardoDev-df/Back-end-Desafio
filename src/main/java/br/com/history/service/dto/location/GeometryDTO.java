package br.com.history.service.dto.location;

public class GeometryDTO {

    private LocationDTO location;

    public GeometryDTO() {
    }

    public GeometryDTO(LocationDTO location) {
        this.location = location;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }
}
