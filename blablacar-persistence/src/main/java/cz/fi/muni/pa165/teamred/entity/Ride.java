package cz.fi.muni.pa165.teamred.entity;

import javax.persistence.Entity;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    private LocalDateTime departure;

    @NotNull
    private double price;

    @NotNull
    private int availableSeats;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "DRIVER_ID")
    private Driver driver;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PASSENGER_ID")
    private Set<Passenger> passengers;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Place sourceCity;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Place destinationCity;

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
    public void setDeparture(LocalDateTime dep){
        this.departure = dep;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setAvailableSeats(int seats){
        this.availableSeats = seats;
    }

    public void setDriver(Driver d){
        this.driver = d;
    }

    public void addPassenger(Passenger p){
        this.passengers.add(p);
    }

    public void setSourceCity(Place city){
        this.sourceCity = city;
    }

    public void setDestinationCity(Place city){
        this.destinationCity = city;
    }

    public void addComment(Comment c){
        this.comments.add(c);
    }

    // getters
    public LocalDateTime getDeparture(){
        return this.departure
    }

    public double getPrice(){
        return this.price;
    }

    public int getAvailableSeats(){
        return this.availableSeats;
    }

    public Driver getDriver(){
       return this.driver;
    }

    public Set<Passenger> getPassengers(){
        return passengers;                          //return Collections.unmodifiableSet(passengers);
    }

    public Place getSourceCity(){
        return this.sourceCity;
    }

    public Place getDestinationCity(){
        return this.destinationCity;
    }

    public Set<Comment> getComments(){
        return comments;                           //return Collections.unmodifiableSet(comments);
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
        } else if ((driver.equals(other.getDriver())) && (departure.equals(other.departure)))
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
