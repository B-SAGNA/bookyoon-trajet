package sn.sonatel.dsi.ins.imoc.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link sn.sonatel.dsi.ins.imoc.domain.Trajet} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrajetDTO implements Serializable {

    private Long id;

    @NotNull
    private String pointDepart;

    @NotNull
    private String pointArrive;

    @NotNull
    private ZonedDateTime dateHeure;

    @NotNull
    private Integer placeDispo;

    @NotNull
    private Float prix;

    @Lob
    private String itineraire;

    @NotNull
    private String userLogin;

    @NotNull
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPointDepart() {
        return pointDepart;
    }

    public void setPointDepart(String pointDepart) {
        this.pointDepart = pointDepart;
    }

    public String getPointArrive() {
        return pointArrive;
    }

    public void setPointArrive(String pointArrive) {
        this.pointArrive = pointArrive;
    }

    public ZonedDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(ZonedDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public Integer getPlaceDispo() {
        return placeDispo;
    }

    public void setPlaceDispo(Integer placeDispo) {
        this.placeDispo = placeDispo;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public String getItineraire() {
        return itineraire;
    }

    public void setItineraire(String itineraire) {
        this.itineraire = itineraire;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrajetDTO)) {
            return false;
        }

        TrajetDTO trajetDTO = (TrajetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, trajetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrajetDTO{" +
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
