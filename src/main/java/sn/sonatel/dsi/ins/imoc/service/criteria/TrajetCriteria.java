package sn.sonatel.dsi.ins.imoc.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import sn.sonatel.dsi.ins.imoc.security.SecurityUtils;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link sn.sonatel.dsi.ins.imoc.domain.Trajet} entity. This class is used
 * in {@link sn.sonatel.dsi.ins.imoc.web.rest.TrajetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trajets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrajetCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter pointDepart;

    private StringFilter pointArrive;

    private ZonedDateTimeFilter dateHeure;

    private IntegerFilter placeDispo;

    private FloatFilter prix;

    private StringFilter userLogin;

    private BooleanFilter deleted;

    private Boolean distinct;

    public TrajetCriteria() {}

    public TrajetCriteria(TrajetCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.pointDepart = other.optionalPointDepart().map(StringFilter::copy).orElse(null);
        this.pointArrive = other.optionalPointArrive().map(StringFilter::copy).orElse(null);
        this.dateHeure = other.optionalDateHeure().map(ZonedDateTimeFilter::copy).orElse(null);
        this.placeDispo = other.optionalPlaceDispo().map(IntegerFilter::copy).orElse(null);
        this.prix = other.optionalPrix().map(FloatFilter::copy).orElse(null);
        this.userLogin = other.optionalUserLogin().map(StringFilter::copy).orElse(null);
        this.deleted = other.optionalDeleted().map(BooleanFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    public void setupConnectedUser() {
        var loginCriteria = new StringFilter();
        loginCriteria.setEquals(SecurityUtils.getCurrentUserLogin().orElseThrow());

        setUserLogin(loginCriteria);
    }

    @Override
    public TrajetCriteria copy() {
        return new TrajetCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPointDepart() {
        return pointDepart;
    }

    public Optional<StringFilter> optionalPointDepart() {
        return Optional.ofNullable(pointDepart);
    }

    public StringFilter pointDepart() {
        if (pointDepart == null) {
            setPointDepart(new StringFilter());
        }
        return pointDepart;
    }

    public void setPointDepart(StringFilter pointDepart) {
        this.pointDepart = pointDepart;
    }

    public StringFilter getPointArrive() {
        return pointArrive;
    }

    public Optional<StringFilter> optionalPointArrive() {
        return Optional.ofNullable(pointArrive);
    }

    public StringFilter pointArrive() {
        if (pointArrive == null) {
            setPointArrive(new StringFilter());
        }
        return pointArrive;
    }

    public void setPointArrive(StringFilter pointArrive) {
        this.pointArrive = pointArrive;
    }

    public ZonedDateTimeFilter getDateHeure() {
        return dateHeure;
    }

    public Optional<ZonedDateTimeFilter> optionalDateHeure() {
        return Optional.ofNullable(dateHeure);
    }

    public ZonedDateTimeFilter dateHeure() {
        if (dateHeure == null) {
            setDateHeure(new ZonedDateTimeFilter());
        }
        return dateHeure;
    }

    public void setDateHeure(ZonedDateTimeFilter dateHeure) {
        this.dateHeure = dateHeure;
    }

    public IntegerFilter getPlaceDispo() {
        return placeDispo;
    }

    public Optional<IntegerFilter> optionalPlaceDispo() {
        return Optional.ofNullable(placeDispo);
    }

    public IntegerFilter placeDispo() {
        if (placeDispo == null) {
            setPlaceDispo(new IntegerFilter());
        }
        return placeDispo;
    }

    public void setPlaceDispo(IntegerFilter placeDispo) {
        this.placeDispo = placeDispo;
    }

    public FloatFilter getPrix() {
        return prix;
    }

    public Optional<FloatFilter> optionalPrix() {
        return Optional.ofNullable(prix);
    }

    public FloatFilter prix() {
        if (prix == null) {
            setPrix(new FloatFilter());
        }
        return prix;
    }

    public void setPrix(FloatFilter prix) {
        this.prix = prix;
    }

    public StringFilter getUserLogin() {
        return userLogin;
    }

    public Optional<StringFilter> optionalUserLogin() {
        return Optional.ofNullable(userLogin);
    }

    public StringFilter userLogin() {
        if (userLogin == null) {
            setUserLogin(new StringFilter());
        }
        return userLogin;
    }

    public void setUserLogin(StringFilter userLogin) {
        this.userLogin = userLogin;
    }

    public BooleanFilter getDeleted() {
        return deleted;
    }

    public Optional<BooleanFilter> optionalDeleted() {
        return Optional.ofNullable(deleted);
    }

    public BooleanFilter deleted() {
        if (deleted == null) {
            setDeleted(new BooleanFilter());
        }
        return deleted;
    }

    public void setDeleted(BooleanFilter deleted) {
        this.deleted = deleted;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TrajetCriteria that = (TrajetCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(pointDepart, that.pointDepart) &&
            Objects.equals(pointArrive, that.pointArrive) &&
            Objects.equals(dateHeure, that.dateHeure) &&
            Objects.equals(placeDispo, that.placeDispo) &&
            Objects.equals(prix, that.prix) &&
            Objects.equals(userLogin, that.userLogin) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pointDepart, pointArrive, dateHeure, placeDispo, prix, userLogin, deleted, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrajetCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPointDepart().map(f -> "pointDepart=" + f + ", ").orElse("") +
            optionalPointArrive().map(f -> "pointArrive=" + f + ", ").orElse("") +
            optionalDateHeure().map(f -> "dateHeure=" + f + ", ").orElse("") +
            optionalPlaceDispo().map(f -> "placeDispo=" + f + ", ").orElse("") +
            optionalPrix().map(f -> "prix=" + f + ", ").orElse("") +
            optionalUserLogin().map(f -> "userLogin=" + f + ", ").orElse("") +
            optionalDeleted().map(f -> "deleted=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
