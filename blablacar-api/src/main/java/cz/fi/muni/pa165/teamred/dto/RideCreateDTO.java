package cz.fi.muni.pa165.teamred.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * DataTransferObject for creating Ride
 *
 * @author Šimon Mačejovský
 */
public class RideCreateDTO {

    @NotNull
    private Date departure;

    @NotNull
    private Long driverId;

    @NotNull
    private Long sourcePlaceId;

    @NotNull
    private  Long destinationPlaceId;

    @NotNull
    private double seatPrize;

    @NotNull
    private int seatsAvailable;


//    private UserDTO driver;

    /**
     * Constructor
     */
    public RideCreateDTO() {}

    // getters and setters
    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public Long getSourcePlaceId() {
        return sourcePlaceId;
    }

    public void setSourcePlaceId(Long sourcePlaceId) {
        this.sourcePlaceId = sourcePlaceId;
    }

    public Long getDestinationPlaceId() {
        return destinationPlaceId;
    }

    public void setDestinationPlaceId(Long destinationPlaceid) {
        this.destinationPlaceId = destinationPlaceid;
    }

    public double getSeatPrize() {
        return seatPrize;
    }

    public void setSeatPrize(double seatPrize) {
        this.seatPrize = seatPrize;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RideCreateDTO)) return false;

        RideCreateDTO that = (RideCreateDTO) o;

        if (Double.compare(that.getSeatPrize(), getSeatPrize()) != 0) return false;
        if (getSeatsAvailable() != that.getSeatsAvailable()) return false;
        if (getDeparture() != null ? !getDeparture().equals(that.getDeparture()) : that.getDeparture() != null)
            return false;
        return getDriverId() != null ? getDriverId().equals(that.getDriverId()) : that.getDriverId() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getDeparture() != null ? getDeparture().hashCode() : 0;
        result = 31 * result + (getDriverId() != null ? getDriverId().hashCode() : 0);
        temp = Double.doubleToLongBits(getSeatPrize());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getSeatsAvailable();
        return result;
    }
}
