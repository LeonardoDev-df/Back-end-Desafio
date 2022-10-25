package br.com.history.service.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.Objects;

public class HistoricalSiteMapDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private Set<SiteImageMapDTO> siteImageMapDTOS;

    private String streetAddress;

    private String city;

    private String uf;

    private String zipCode;

    private String longitude;

    private String latitude;

    public HistoricalSiteMapDTO() {}

    public HistoricalSiteMapDTO(Long id, String name, String description, Set<SiteImageMapDTO> siteImageMapDTOS, String longitude, String latitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.siteImageMapDTOS = siteImageMapDTOS;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public HistoricalSiteMapDTO(Long id, String name, String description, String longitude, String latitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public HistoricalSiteMapDTO(Long id, String name, String description,
            String streetAddress, String city, String uf,
            String zipCode, String longitude, String latitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.streetAddress = streetAddress;
        this.city = city;
        this.uf = uf;
        this.zipCode = zipCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SiteImageMapDTO> getSiteImageMapDTOS() {
        return siteImageMapDTOS;
    }

    public void setSiteImageMapDTOS(Set<SiteImageMapDTO> siteImageMapDTOS) {
        this.siteImageMapDTOS = siteImageMapDTOS;
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

    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoricalSiteMapDTO)) return false;
        HistoricalSiteMapDTO that = (HistoricalSiteMapDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(siteImageMapDTOS, that.siteImageMapDTOS) && Objects.equals(longitude, that.longitude) && Objects.equals(latitude, that.latitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, siteImageMapDTOS, longitude, latitude);
    }

    @Override
    public String toString() {
        return "HistoricalSiteMapDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", siteImageMapDTOS=" + siteImageMapDTOS +
            ", longitude='" + longitude + '\'' +
            ", latitude='" + latitude + '\'' +
            '}';
    }
}
