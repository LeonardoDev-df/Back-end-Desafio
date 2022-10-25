package br.com.history.domain;

import br.com.history.domain.enumeration.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HistoricalSite.
 */
@Entity
@Table(name = "historical_site")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HistoricalSite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "jhi_like")
    private Integer like;

    @Column(name = "comment")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @Column(name = "link")
    private String link;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "historicalSite", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "historicalSite" }, allowSetters = true)
    private Set<SiteImage> siteImages = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_historical_site__user_id"))
    private User user;

    public HistoricalSite() {}

    public HistoricalSite(
        Long id,
        String name,
        String description,
        Integer like,
        String comment,
        StatusEnum status,
        String link,
        Address address,
        Set<SiteImage> siteImages,
        User user
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.like = like;
        this.comment = comment;
        this.status = status;
        this.link = link;
        this.address = address;
        this.siteImages = siteImages;
        this.user = user;
    }

    public HistoricalSite(Long id, String name, String description, Address address, Set<SiteImage> siteImages) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.siteImages = siteImages;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HistoricalSite id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public HistoricalSite name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public HistoricalSite description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLike() {
        return this.like;
    }

    public HistoricalSite like(Integer like) {
        this.like = like;
        return this;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public String getComment() {
        return this.comment;
    }

    public HistoricalSite comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public StatusEnum getStatus() {
        return this.status;
    }

    public HistoricalSite status(StatusEnum status) {
        this.status = status;
        return this;
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

    public Address getAddress() {
        return this.address;
    }

    public HistoricalSite address(Address address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<SiteImage> getSiteImages() {
        return this.siteImages;
    }

    public HistoricalSite siteImages(Set<SiteImage> siteImages) {
        this.setSiteImages(siteImages);
        return this;
    }

    public HistoricalSite addSiteImage(SiteImage siteImage) {
        this.siteImages.add(siteImage);
        siteImage.setHistoricalSite(this);
        return this;
    }

    public void setSiteImages(Set<SiteImage> siteImages) {
        if (this.siteImages != null) {
            this.siteImages.forEach(i -> i.setHistoricalSite(null));
        }
        if (siteImages != null) {
            siteImages.forEach(i -> i.setHistoricalSite(this));
        }
        this.siteImages = siteImages;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistoricalSite)) {
            return false;
        }
        return id != null && id.equals(((HistoricalSite) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HistoricalSite{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", like=" + getLike() +
            ", link=" + getLink() +
            ", comment='" + getComment() + "'" +
            ", status='" + getStatus() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }
}
