package cz.fi.muni.pa165.teamred.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * This class represents a Ride in our application.
 * Created by Šimon on 25.10.2017.
 */
@Entity
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date departure;

    @NotNull
    private double price;

    @NotNull
    private int availableSeats;

    @NotNull
    @ManyToOne
    private Driver driver;

    @ManyToMany
    private Set<Passenger> passengers;

    @NotNull
    @ManyToOne
    private Place sourcePlace;

    @NotNull
    @ManyToOne
    private Place destinationPlace;

    @ManyToMany
    private Set<Comment> comments;
    
    /**
     * Default Constructor
     */
    public Ride() {
    }

    /**
     * Constructor
     * @param rideId id
     */
    public Ride(Long rideId) {
        this.id = rideId;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setDeparture(Date dep) {
        this.departure = dep;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailableSeats(int seats) {
        this.availableSeats = seats;
    }

    public void setDriver(Driver d) {
        this.driver = d;
    }

    public void addPassenger(Passenger p) {
        this.passengers.add(p);
    }

    public void setSourcePlace(Place place) {
        this.sourcePlace = place;
    }

    public void setDestinationPlace(Place place) {
        this.destinationPlace = place;
    }

    public void addComment(Comment c) {
        this.comments.add(c);
    }

    // getters
    public Long getId() {
        return id;
    }

    public Date getDeparture() {
        return this.departure;
    }

    public double getPrice() {
        return this.price;
    }

    public int getAvailableSeats() {
        return this.availableSeats;
    }

    public Driver getDriver() {
       return this.driver;
    }

    public Set<Passenger> getPassengers() {
        return Collections.unmodifiableSet(passengers);
    }

    public Place getSourcePlace() {
        return this.sourcePlace;
    }

    public Place getDestinationPlace() {
        return this.destinationPlace;
    }

    public Set<Comment> getComments() {
        return Collections.unmodifiableSet(comments);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (! (obj instanceof Ride))
            return false;
        Ride other = (Ride) obj;
        if (driver == null) {
            if (other.getDriver() != null)
                return false;
        } else if ((driver.equals(other.getDriver())) && (departure.equals(other.getDeparture())))
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime*result+((driver == null)? 0 : driver.hashCode())+((departure == null)? 0 : departure.hashCode());
        return result;
    }
}
