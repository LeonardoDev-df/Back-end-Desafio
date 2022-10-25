package br.com.history.service.dto;

import javax.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.history.domain.SiteImage} entity.
 */
public class SiteImageDTO implements Serializable {

    private Long id;

    private Integer numberImage;

    @Lob
    private byte[] image3D;

    @Lob
    private byte[] imagePreview;

    private Integer year;

    public SiteImageDTO() {
    }

    public SiteImageDTO(Long id, Integer numberImage, byte[] image3D, byte[] imagePreview, Integer year) {
        this.id = id;
        this.numberImage = numberImage;
        this.image3D = image3D;
        this.imagePreview = imagePreview;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberImage() {
        return numberImage;
    }

    public void setNumberImage(Integer numberImage) {
        this.numberImage = numberImage;
    }

    public byte[] getImage3D() {
        return image3D;
    }

    public void setImage3D(byte[] image3D) {
        this.image3D = image3D;
    }

    public byte[] getImagePreview() {
        return imagePreview;
    }

    public void setImagePreview(byte[] imagePreview) {
        this.imagePreview = imagePreview;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteImageDTO)) {
            return false;
        }

        SiteImageDTO siteImageDTO = (SiteImageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, siteImageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteImageDTO{" +
            "id=" + getId() +
            ", numberImage=" + getNumberImage() +
            ", image3D='" + getImage3D() + "'" +
            ", imagePreview='" + getImagePreview() + "'" +
            ", year='" + getYear() +
            "}";
    }
}
