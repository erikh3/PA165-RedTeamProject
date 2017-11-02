package cz.fi.muni.pa165.teamred.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * This class represents a User in our application.
 *
 * Created by Jozef Cibík on 25.10.2017.
 */
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;


    @NotNull(message = "User Name cannot be null.")
    @Column(updatable = false, nullable = false)
    @Size(
            min = 2,
            max = 255,
            message = "Name must be longer than 2 characters and shorter than 255 characters."
    )
    private String name;


    @NotNull(message = "User Surename cannot be null.")
    @Column(updatable = false, nullable = false)
    private String surename;


    @NotNull(message = "User Nickname cannot be null.")
    @Column(updatable = false, nullable = false, unique = true)
    private String nickname;

    @OneToMany(mappedBy = "author")
    private Set<Comment> userComments;

    //______________________________________________________________________________________________________Constructors

    /**
    * Default Constructor
    */
    public User() {
    }


    //_______________________________________________________________________________________________Getters and Setters

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

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Set<Comment> getUserComments() {
        return userComments;
    }

    public void setUserComments(Set<Comment> userComments) {
        this.userComments = userComments;
    }

    //________________________________________________________________________________________Equals and HashCode method

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return getNickname() != null ? getNickname().equals(user.getNickname()) : user.getNickname() == null;
    }

    @Override
    public int hashCode() {
        return getNickname() != null ? getNickname().hashCode() : 0;
    }
}
