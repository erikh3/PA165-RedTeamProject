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
    private UserDTO driver;

    /**
     * Constructor
     */
    public RideCreateDTO() {}

    // getters and setters
    public UserDTO getDriver() {
        return driver;
    }

    public void setDriver(UserDTO driver) {
        this.driver = driver;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RideDTO)) return false;

        RideDTO other = (RideDTO) obj;

        return (Objects.equals(driver, other.getDriver()) &&
                Objects.equals(departure,other.getDeparture()));
    }

    @Override
    public int hashCode() {
        int result = getDeparture().hashCode();
        result = 31 * result + driver.hashCode();
        return result;
    }
}
