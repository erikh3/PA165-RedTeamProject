package cz.fi.muni.pa165.teamred.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
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

    @OneToMany(mappedBy = "sourcePlace", cascade=CascadeType.PERSIST)  
    private Set<Ride> originatingRides = new HashSet<>();
    
    @OneToMany(mappedBy = "destinationPlace", cascade=CascadeType.PERSIST)  
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
        return originatingRides;
    }

    public void setOriginatingRides(Set<Ride> originatingRides) {
        this.originatingRides = originatingRides;
    }

    public Set<Ride> getDestinationRides() {
        return destinationRides;
    }

    public void setDestinationRides(Set<Ride> destinationRides) {
        this.destinationRides = destinationRides;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Place)) return false;

        Place other = (Place) o;

        return Objects.equals(name, other.getName());
    }

    @Override
    public int hashCode() {
        return 31 + (name == null ? 0 : name.hashCode());
    }
}
