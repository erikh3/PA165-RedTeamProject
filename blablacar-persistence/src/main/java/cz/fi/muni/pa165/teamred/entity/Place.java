package cz.fi.muni.pa165.teamred.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Persistent entity Place
 *
 * @author Erik Horváth
 */
@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "sourcePlace")
    private Set<Ride> originatingRides = new HashSet<>();
    
    @OneToMany(mappedBy = "destinationPlace")
    private Set<Ride> destinationRides = new HashSet<>();

    //constructors
    public Place() {
    }

    public Place(String name) {
        this.name = name;
    }

    //getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Ride> getOriginatingRides() {
        return Collections.unmodifiableSet(originatingRides);
    }

    public void setOriginatingRides(Set<Ride> originatingRides) {
        this.originatingRides = originatingRides;
    }

    public Set<Ride> getDestinationRides() {
        return Collections.unmodifiableSet(this.destinationRides);
    }

    public void setDestinationRides(Set<Ride> destinationRides) {
        this.destinationRides = destinationRides;
    }

    //add and remove
    public void addOriginatingRide(Ride ride){
        this.originatingRides.add(ride);
    }

    public void addDestinationRide(Ride ride){
        this.destinationRides.add(ride);
    }

    public boolean removeOriginatingRide(Ride ride){
        return this.originatingRides.remove(ride);
    }

    public boolean removeDestinationRide(Ride ride){
        return this.destinationRides.remove(ride);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Place)) return false;

        Place other = (Place) o;

        return Objects.equals(name, other.getName());
    }

    @Override
    public int hashCode() {
        return 31 + (name == null ? 0 : name.hashCode());
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
