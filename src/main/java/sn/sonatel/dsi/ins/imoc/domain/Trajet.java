package sn.sonatel.dsi.ins.imoc.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Trajet.
 */
@Entity
@Table(name = "trajet")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Trajet extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "point_depart", nullable = false)
    private String pointDepart;

    @NotNull
    @Column(name = "point_arrive", nullable = false)
    private String pointArrive;

    @NotNull
    @Column(name = "date_heure", nullable = false)
    private ZonedDateTime dateHeure;

    @NotNull
    @Column(name = "place_dispo", nullable = false)
    private Integer placeDispo;

    @NotNull
    @Column(name = "prix", nullable = false)
    private Float prix;

    @Lob
    @Column(name = "itineraire")
    private String itineraire;

    @NotNull
    @Column(name = "user_login", nullable = false)
    private String userLogin;

    @NotNull
    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Trajet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPointDepart() {
        return this.pointDepart;
    }

    public Trajet pointDepart(String pointDepart) {
        this.setPointDepart(pointDepart);
        return this;
    }

    public void setPointDepart(String pointDepart) {
        this.pointDepart = pointDepart;
    }

    public String getPointArrive() {
        return this.pointArrive;
    }

    public Trajet pointArrive(String pointArrive) {
        this.setPointArrive(pointArrive);
        return this;
    }

    public void setPointArrive(String pointArrive) {
        this.pointArrive = pointArrive;
    }

    public ZonedDateTime getDateHeure() {
        return this.dateHeure;
    }

    public Trajet dateHeure(ZonedDateTime dateHeure) {
        this.setDateHeure(dateHeure);
        return this;
    }

    public void setDateHeure(ZonedDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public Integer getPlaceDispo() {
        return this.placeDispo;
    }

    public Trajet placeDispo(Integer placeDispo) {
        this.setPlaceDispo(placeDispo);
        return this;
    }

    public void setPlaceDispo(Integer placeDispo) {
        this.placeDispo = placeDispo;
    }

    public Float getPrix() {
        return this.prix;
    }

    public Trajet prix(Float prix) {
        this.setPrix(prix);
        return this;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public String getItineraire() {
        return this.itineraire;
    }

    public Trajet itineraire(String itineraire) {
        this.setItineraire(itineraire);
        return this;
    }

    public void setItineraire(String itineraire) {
        this.itineraire = itineraire;
    }

    public String getUserLogin() {
        return this.userLogin;
    }

    public Trajet userLogin(String userLogin) {
        this.setUserLogin(userLogin);
        return this;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Trajet deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trajet)) {
            return false;
        }
        return getId() != null && getId().equals(((Trajet) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Trajet{" +
            "id=" + getId() +
            ", pointDepart='" + getPointDepart() + "'" +
            ", pointArrive='" + getPointArrive() + "'" +
            ", dateHeure='" + getDateHeure() + "'" +
            ", placeDispo=" + getPlaceDispo() +
            ", prix=" + getPrix() +
            ", itineraire='" + getItineraire() + "'" +
            ", userLogin='" + getUserLogin() + "'" +
            ", deleted='" + getDeleted() + "'" +
            "}";
    }
}
