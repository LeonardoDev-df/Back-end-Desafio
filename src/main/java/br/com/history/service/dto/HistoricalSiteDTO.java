package br.com.history.service.dto;

import br.com.history.domain.SiteImage;
import br.com.history.domain.enumeration.StatusEnum;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link br.com.history.domain.HistoricalSite} entity.
 */
public class HistoricalSiteDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private Integer like;

    private String comment;

    private StatusEnum status;

    private String link;

    private AddressDTO address;

    private Set<SiteImage> siteImages;

    private Long idUser;

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

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public Set<SiteImage> getSiteImages() {
        return siteImages;
    }

    public void setSiteImages(Set<SiteImage> siteImages) {
        this.siteImages = siteImages;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistoricalSiteDTO)) {
            return false;
        }

        HistoricalSiteDTO historicalSiteDTO = (HistoricalSiteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, historicalSiteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "HistoricalSiteDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", like=" + like +
            ", link=" + link +
            ", comment='" + comment + '\'' +
            ", status=" + status +
            ", address=" + address +
            ", siteImages=" + siteImages +
            ", idUser=" + idUser +
            '}';
    }
}
