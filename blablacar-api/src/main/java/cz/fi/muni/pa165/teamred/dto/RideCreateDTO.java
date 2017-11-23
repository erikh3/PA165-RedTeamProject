package cz.fi.muni.pa165.teamred.dto;

import javax.validation.constraints.NotNull;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RideDTO)) return false;

        RideDTO other = (RideDTO) obj;

        return (Objects.equals(driverId, other.getDriver()) &&
                Objects.equals(departure,other.getDeparture()));
    }

    @Override
    public int hashCode() {
        int result = getDeparture().hashCode();
        result = 31 * result + driverId.hashCode();
        return result;
    }
}
