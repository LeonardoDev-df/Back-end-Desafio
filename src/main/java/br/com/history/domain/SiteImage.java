package br.com.history.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * A SiteImage.
 */
@Entity
@Table(name = "site_image")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SiteImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "number_image")
    private Integer numberImage;

    @Lob
    @Column(name = "image_3d")
    private byte[] image3D;

    @Lob
    @Column(name = "image_preview")
    private byte[] imagePreview;

    @Column(name = "year")
    private Integer year;

    @Embedded
    private ButtonPosition buttonPosition;

    @Column(name = "button_color")
    private String buttonColor;

    @ManyToOne
    @JsonIgnoreProperties(value = {"address", "siteImages"}, allowSetters = true)
    @JsonIgnore
    private HistoricalSite historicalSite;

    public SiteImage() {
    }

    public SiteImage(byte[] imagePreview) {
        this.imagePreview = imagePreview;
    }

    public SiteImage(Long id, Integer numberImage, byte[] image3D, byte[] imagePreview, Integer year) {
        this.id = id;
        this.numberImage = numberImage;
        this.image3D = image3D;
        this.imagePreview = imagePreview;
        this.year = year;
    }

    public SiteImage(Long id, Integer numberImage, byte[] image3D, byte[] imagePreview, Integer year, HistoricalSite historicalSite, ButtonPosition buttonPosition) {
        this.id = id;
        this.numberImage = numberImage;
        this.image3D = image3D;
        this.imagePreview = imagePreview;
        this.year = year;
        this.historicalSite = historicalSite;
        this.buttonPosition = buttonPosition;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SiteImage id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNumberImage() {
        return this.numberImage;
    }

    public SiteImage numberImage(Integer numberImage) {
        this.numberImage = numberImage;
        return this;
    }

    public void setNumberImage(Integer numberImage) {
        this.numberImage = numberImage;
    }

    public byte[] getImage3D() {
        return this.image3D;
    }

    public SiteImage image3D(byte[] image3D) {
        this.image3D = image3D;
        return this;
    }

    public void setImage3D(byte[] image3D) {
        this.image3D = image3D;
    }

    public byte[] getImagePreview() {
        return this.imagePreview;
    }

    public SiteImage imagePreview(byte[] imagePreview) {
        this.imagePreview = imagePreview;
        return this;
    }

    public void setImagePreview(byte[] imagePreview) {
        this.imagePreview = imagePreview;
    }

    public Integer getYear() {
        return this.year;
    }

    public SiteImage year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public HistoricalSite getHistoricalSite() {
        return this.historicalSite;
    }

    public SiteImage historicalSite(HistoricalSite historicalSite) {
        this.setHistoricalSite(historicalSite);
        return this;
    }

    public void setHistoricalSite(HistoricalSite historicalSite) {
        this.historicalSite = historicalSite;
    }

    public ButtonPosition getButtonPosition() {
        return buttonPosition;
    }

    public void setButtonPosition(ButtonPosition buttonPosition) {
        this.buttonPosition = buttonPosition;
    }

    public String getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(String buttonColor) {
        this.buttonColor = buttonColor;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteImage)) {
            return false;
        }
        return id != null && id.equals(((SiteImage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteImage{" +
            "id=" + getId() +
            ", numberImage=" + getNumberImage() +
            ", image3D='" + getImage3D() + "'" +
            ", imagePreview='" + getImagePreview() + "'" +
            ", year='" + getYear() + "'" +
            "}";
    }
}
