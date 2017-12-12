package cz.fi.muni.pa165.teamred.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

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
    private int availableSeats;

    @NotNull
    private double seatPrize;



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

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getSeatPrize() {
        return seatPrize;
    }

    public void setSeatPrize(double seatPrize) {
        this.seatPrize = seatPrize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RideCreateDTO that = (RideCreateDTO) o;

        if (availableSeats != that.availableSeats) return false;
        if (Double.compare(that.seatPrize, seatPrize) != 0) return false;
        if (departure != null ? !departure.equals(that.departure) : that.departure != null) return false;
        return driverId != null ? driverId.equals(that.driverId) : that.driverId == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = departure != null ? departure.hashCode() : 0;
        result = 31 * result + (driverId != null ? driverId.hashCode() : 0);
        result = 31 * result + availableSeats;
        temp = Double.doubleToLongBits(seatPrize);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
