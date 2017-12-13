package cz.fi.muni.pa165.teamred.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * This class represents a Ride in our application.
 *
 * @author Šimon Mačejovský
 */
@Entity
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date departure;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private int availableSeats;

    @NotNull
    @Column(nullable = false)
    private double seatPrice;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Place sourcePlace;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Place destinationPlace;

    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User driver;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> passengers = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setDeparture(Date dep) {
        this.departure = dep;
    }

    public void setSeatPrice(double seatPrice) {
        this.seatPrice = seatPrice;
    }

    public void setAvailableSeats(int seats) {
        this.availableSeats = seats;
    }

    public void setDriver(User d) {
        this.driver = d;
    }

    public void setSourcePlace(Place place) {
        this.sourcePlace = place;
    }

    public void setDestinationPlace(Place place) {
        this.destinationPlace = place;
    }

    public void setPassengers(Set<User> passengers) {
        this.passengers = passengers;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    // getters
    public Long getId() {
        return id;
    }

    public Date getDeparture() {
        return this.departure;
    }

    public double getSeatPrice() {
        return this.seatPrice;
    }

    public int getAvailableSeats() {
        return this.availableSeats;
    }

    public User getDriver() {
       return this.driver;
    }

    public Place getSourcePlace() {
        return this.sourcePlace;
    }

    public Place getDestinationPlace() {
        return this.destinationPlace;
    }

    public Set<User> getPassengers() {
        return new HashSet<>(passengers);
    }

    public Set<Comment> getComments() {
        return new HashSet<>(comments);
    }

    //adders
    public void addPassenger(User p) {
        this.passengers.add(p);
    }

    public void addComment(Comment c) {
        this.comments.add(c);
    }

    //removers
    public boolean removePassenger(User p){
        return this.passengers.remove(p);
    }

    public boolean removeComment(Comment c){
        return this.comments.remove(c);
    }

    //equals and hash
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Ride)) return false;

        Ride other = (Ride) obj;

        return (Objects.equals(driver, other.getDriver()) &&
                Objects.equals(departure,other.getDeparture()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((driver == null) ? 0 : driver.hashCode());
        result = prime * result + ((departure == null) ? 0 : departure.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id=" + id +
                ", departure=" + departure +
                ", availableSeats=" + availableSeats +
                ", seatPrice=" + seatPrice +
                ", sourcePlace=" + sourcePlace +
                ", destinationPlace=" + destinationPlace +
                ", driver=" + driver +
                '}';
    }
}
