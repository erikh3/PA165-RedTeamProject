package cz.fi.muni.pa165.teamred.dto;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DataTransferObject for Place
 *
 * @author miroslav.laco@gmail.com
 */
public class PlaceDTO {

    private Long id;
    private String name;
    private Set<RideDTO> originatingRides = new HashSet<>();
    private Set<RideDTO> destinationRides = new HashSet<>();

    //Getters + Setters

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

    public Set<RideDTO> getOriginatingRides() {
        return Collections.unmodifiableSet(originatingRides);
    }

    public void setOriginatingRides(Set<RideDTO> originatingRides) {
        this.originatingRides = originatingRides;
    }

    public Set<RideDTO> getDestinationRides() {
        return Collections.unmodifiableSet(destinationRides);
    }

    public void setDestinationRides(Set<RideDTO> destinationRides) {
        this.destinationRides = destinationRides;
    }

    //Adders + Removers

    public boolean addOriginatingRide(RideDTO ride) {
        return originatingRides.add(ride);
    }

    public boolean removeOriginatingRide(RideDTO ride) {
        return originatingRides.remove(ride);
    }

    public boolean addDestinationRide(RideDTO ride) {
        return destinationRides.add(ride);
    }

    public boolean removeDestinationRide(RideDTO ride) {
        return destinationRides.remove(ride);
    }

    //Equals + Hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceDTO)) return false;

        PlaceDTO placeDTO = (PlaceDTO) o;

        return Objects.equals(getName(),placeDTO.getName());
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    //ToString

    @Override
    public String toString() {
        return "PlaceDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", originatingRides=" + originatingRides +
                ", destinationRides=" + destinationRides +
                '}';
    }
}
