package cz.fi.muni.pa165.teamred.dto;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jozef Cibik
 */
public class UserDTO {

    private Long id;
    private String name;
    private String surname;
    private String nickname;
    private Set<RideDTO> ridesAsDriver = new HashSet<>();
    private Set<RideDTO> ridesAsPassenger = new HashSet<>();
    private Set<CommentDTO> comments = new HashSet<>();

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Set<RideDTO> getRidesAsDriver() {
        return Collections.unmodifiableSet(this.ridesAsDriver);
    }

    public void setRidesAsDriver(Set<RideDTO> ridesAsDriver) {
        this.ridesAsDriver = ridesAsDriver;
    }

    public Set<RideDTO> getRidesAsPassenger() {
        return Collections.unmodifiableSet(this.ridesAsPassenger);
    }

    public void setRidesAsPassenger(Set<RideDTO> ridesAsPassenger) {
        this.ridesAsPassenger = ridesAsPassenger;
    }

    public Set<CommentDTO> getComments() {
        return Collections.unmodifiableSet(this.comments);
    }

    public void setComments(Set<CommentDTO> comments) {
        this.comments = comments;
    }

    //____________________________________________________________________________________________________Add and remove

    public boolean addRideAsDriver(RideDTO ride){
        return this.ridesAsDriver.add(ride);
    }

    public boolean removeRideAsDriver(RideDTO ride){
        return this.ridesAsDriver.remove(ride);
    }

    public boolean addRideAsPassenger(RideDTO ride){
        return this.ridesAsPassenger.add(ride);
    }

    public boolean removeRideAsPassenger(RideDTO ride){
        return this.ridesAsPassenger.remove(ride);
    }

    public boolean addComment(CommentDTO comment){
        return this.comments.add(comment);
    }

    public boolean removeComment(CommentDTO comment){
        return this.comments.remove(comment);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof UserDTO)) return false;

        UserDTO userDTO = (UserDTO) o;

        return getNickname() != null ? getNickname().equals(userDTO.getNickname()) : userDTO.getNickname() == null;
    }

    @Override
    public int hashCode() {
        return getNickname() != null ? getNickname().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
