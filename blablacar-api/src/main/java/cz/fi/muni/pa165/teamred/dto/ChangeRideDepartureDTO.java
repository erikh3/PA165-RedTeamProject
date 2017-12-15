package cz.fi.muni.pa165.teamred.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ChangeRideDepartureDTO {
    @NotNull
    private Long rideId;

    @NotNull
    private Date departure;

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChangeRideDepartureDTO that = (ChangeRideDepartureDTO) o;

        if (rideId != null ? !rideId.equals(that.rideId) : that.rideId != null) return false;
        return departure != null ? departure.equals(that.departure) : that.departure == null;
    }

    @Override
    public int hashCode() {
        int result = rideId != null ? rideId.hashCode() : 0;
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChangeRideDepartureDTO{" +
                "rideId=" + rideId +
                ", departure=" + departure +
                '}';
    }
}
