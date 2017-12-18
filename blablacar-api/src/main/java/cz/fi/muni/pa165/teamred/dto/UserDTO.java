package cz.fi.muni.pa165.teamred.dto;

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
    private String loginId;
    private Set<Long> ridesAsDriver = new HashSet<>();
    private Set<Long> ridesAsPassenger = new HashSet<>();
    private Set<Long> comments = new HashSet<>();

    private boolean isAdmin;

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

    public Set<Long> getRidesAsDriver() {
        return this.ridesAsDriver;
    }

    public void setRidesAsDriver(Set<Long> ridesAsDriver) {
        this.ridesAsDriver = ridesAsDriver;
    }

    public Set<Long> getRidesAsPassenger() {
        return this.ridesAsPassenger;
    }

    public void setRidesAsPassenger(Set<Long> ridesAsPassenger) {
        this.ridesAsPassenger = ridesAsPassenger;
    }

    public Set<Long> getComments() {
        return this.comments;
    }

    public void setComments(Set<Long> comments) {
        this.comments = comments;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    //____________________________________________________________________________________________________Add and remove

    public boolean addRideAsDriver(Long rideId){
        return this.ridesAsDriver.add(rideId);
    }

    public boolean removeRideAsDriver(Long rideId){
        return this.ridesAsDriver.remove(rideId);
    }

    public boolean addRideAsPassenger(Long rideId){
        return this.ridesAsPassenger.add(rideId);
    }

    public boolean removeRideAsPassenger(Long rideId){
        return this.ridesAsPassenger.remove(rideId);
    }

    public boolean addComment(Long comment){
        return this.comments.add(comment);
    }

    public boolean removeComment(Long comment){
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
