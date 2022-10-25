package br.com.history.service.dto.location;

import java.math.BigDecimal;

public class LocationDTO {

    private BigDecimal lat;
    private BigDecimal lng;

    public LocationDTO() {
    }

    public LocationDTO(BigDecimal lat, BigDecimal lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }
}
