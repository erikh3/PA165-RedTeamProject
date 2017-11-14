package cz.fi.muni.pa165.teamred.dto;



import java.util.*;

/**
 * @author Šimon Mačejovský
 */
public class RideDTO {
    private Long id;
    private Date departure;
    private int availableSeats;
    private double seatPrice;
    private PlaceDTO sourcePlace;
    private  PlaceDTO destinationPlace;
    private UserDTO driver;
    private Set<UserDTO> passengers = new HashSet<>();
    private Set<CommentDTO> comments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(double seatPrice) {
        this.seatPrice = seatPrice;
    }

    public PlaceDTO getSourcePlace() {
        return sourcePlace;
    }

    public void setSourcePlace(PlaceDTO sourcePlace) {
        this.sourcePlace = sourcePlace;
    }

    public PlaceDTO getDestinationPlace() {
        return destinationPlace;
    }

    public void setDestinationPlace(PlaceDTO destinationPlace) {
        this.destinationPlace = destinationPlace;
    }

    public UserDTO getDriver() {
        return driver;
    }

    public void setDriver(UserDTO driver) {
        this.driver = driver;
    }

    public Set<UserDTO> getPassengers() {
        return this.passengers;
    }

    public void setPassengers(Set<UserDTO> passengers) {
        this.passengers = passengers;
    }

    public Set<CommentDTO> getComments() {
        return this.comments;
    }

    public void setComments(Set<CommentDTO> comments) {
        this.comments = comments;
    }

    public boolean addPassenger(UserDTO psg){
        return this.passengers.add(psg);
    }

    public boolean removePassenger(UserDTO psg){
        return this.passengers.remove(psg);
    }

    public boolean addComment(CommentDTO comment){
        return this.comments.add(comment);
    }

    public boolean removeComment(CommentDTO comment){
        return this.comments.remove(comment);
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
        result = 31 * result + getDriver().hashCode();
        return result;
    }
}
