package cz.fi.muni.pa165.teamred.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Erik Horváth
 */
@Entity
public class Driver/* extends User*/ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String carDescription;

    private String note;

//    private Set<Ride> rides;

    public Driver() {

    }

    public Driver(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
//
//    public Set<Ride> getRides() {
//        return Collections.unmodifiableSet(rides);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Driver)) return false;

        Driver other = (Driver) o;

        return Objects.equals(carDescription, other.getCarDescription()) && Objects.equals(note, other.getNote());
    }

    @Override
    public int hashCode() {
        int result = carDescription != null ? carDescription.hashCode() : 0;
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }
}
