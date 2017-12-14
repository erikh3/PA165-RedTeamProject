package cz.fi.muni.pa165.teamred.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Erik Horváth
 */
@SuppressWarnings("unused")
public class AddPassengerDTO {
    @NotNull
    private Long passengerId;

    @NotNull
    private Long rideId;

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddPassengerDTO that = (AddPassengerDTO) o;

        if (passengerId != null ? !passengerId.equals(that.passengerId) : that.passengerId != null) return false;
        return rideId != null ? rideId.equals(that.rideId) : that.rideId == null;
    }

    @Override
    public int hashCode() {
        int result = passengerId != null ? passengerId.hashCode() : 0;
        result = 31 * result + (rideId != null ? rideId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AddPassengerDTO{" +
                "passengerId=" + passengerId +
                ", rideId=" + rideId +
                '}';
    }
}
