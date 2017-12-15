package cz.fi.muni.pa165.teamred.dto;

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
    private Set<Long> originatingRides = new HashSet<>();
    private Set<Long> destinationRides = new HashSet<>();

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

    public Set<Long> getOriginatingRides() {
        return originatingRides;
    }

    public void setOriginatingRides(Set<Long> originatingRides) {
        this.originatingRides = originatingRides;
    }

    public Set<Long> getDestinationRides() {
        return destinationRides;
    }

    public void setDestinationRides(Set<Long> destinationRides) {
        this.destinationRides = destinationRides;
    }

    //Adders + Removers

    public boolean addOriginatingRide(Long ride) {
        return originatingRides.add(ride);
    }

    public boolean removeOriginatingRide(Long ride) {
        return originatingRides.remove(ride);
    }

    public boolean addDestinationRide(Long ride) {
        return destinationRides.add(ride);
    }

    public boolean removeDestinationRide(Long ride) {
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
