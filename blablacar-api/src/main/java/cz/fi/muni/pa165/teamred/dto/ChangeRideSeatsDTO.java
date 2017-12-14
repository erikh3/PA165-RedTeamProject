package cz.fi.muni.pa165.teamred.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Erik Horváth
 */
@SuppressWarnings("unused")
public class ChangeRideSeatsDTO {
    @NotNull
    private Long rideId;

    @NotNull
    private int seats;

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChangeRideSeatsDTO that = (ChangeRideSeatsDTO) o;

        if (seats != that.seats) return false;
        return rideId != null ? rideId.equals(that.rideId) : that.rideId == null;
    }

    @Override
    public int hashCode() {
        int result = rideId != null ? rideId.hashCode() : 0;
        result = 31 * result + seats;
        return result;
    }

    @Override
    public String toString() {
        return "ChangeRideSeatsDTO{" +
                "rideId=" + rideId +
                ", seats=" + seats +
                '}';
    }
}
