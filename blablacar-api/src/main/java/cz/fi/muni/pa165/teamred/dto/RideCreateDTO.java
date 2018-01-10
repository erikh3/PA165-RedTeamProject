package cz.fi.muni.pa165.teamred.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * DataTransferObject for creating Ride
 *
 * @author Šimon Mačejovský
 */
public class RideCreateDTO {

    @Future
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull
    private Date departure;

    @NotNull
    private Long driverId;

    @NotNull
    private Long sourcePlaceId;

    @NotNull
    private  Long destinationPlaceId;

    @NotNull
    @Min(value = 1,message = "Cannot be free")
    private double seatPrize;

    @NotNull
    @Min(value = 1, message = "Must be at least one seat available")
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

        if (getDeparture() != null ? !getDeparture().equals(that.getDeparture()) : that.getDeparture() != null)
            return false;
        if (getDriverId() != null ? !getDriverId().equals(that.getDriverId()) : that.getDriverId() != null)
            return false;
        if (getSourcePlaceId() != null ? !getSourcePlaceId().equals(that.getSourcePlaceId()) : that.getSourcePlaceId() != null)
            return false;
        return getDestinationPlaceId() != null ? getDestinationPlaceId().equals(that.getDestinationPlaceId()) : that.getDestinationPlaceId() == null;
    }

    @Override
    public int hashCode() {
        int result = getDeparture() != null ? getDeparture().hashCode() : 0;
        result = 31 * result + (getDriverId() != null ? getDriverId().hashCode() : 0);
        result = 31 * result + (getSourcePlaceId() != null ? getSourcePlaceId().hashCode() : 0);
        result = 31 * result + (getDestinationPlaceId() != null ? getDestinationPlaceId().hashCode() : 0);
        return result;
    }
}
