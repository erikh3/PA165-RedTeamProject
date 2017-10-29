package cz.fi.muni.pa165.teamred.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents User that wants to be a Passenger on a ride
 *
 *  Created by Jozef Cibík on 25.10.2017.
 */
@Entity
public class Passenger extends User {
    @NotNull
    @Column(name = "passengerRides")
    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Ride> rides = new HashSet<Ride>();

    public Passenger(){

    }

    public Set<Ride> getRides() {
        return rides;
    }

    public void setRides(Set<Ride> rides) {
        this.rides = rides;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger)) return false;
        return super.equals(o);

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
