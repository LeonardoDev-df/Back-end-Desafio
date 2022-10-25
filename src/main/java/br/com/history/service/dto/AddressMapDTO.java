package br.com.history.service.dto;

import java.io.Serializable;

public class AddressMapDTO implements Serializable {

    private String longitude;
    private String latitude;

    public AddressMapDTO(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
