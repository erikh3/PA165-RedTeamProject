package cz.fi.muni.pa165.teamred.dto;

import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;

import java.util.Date;
import java.util.Objects;

/**
 * @author Erik Horváth
 */
public class CommentDTO {
    private Long id;
    private Date created;
    private String text;
    private User author;
    private Ride ride;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentDTO that = (CommentDTO) o;

        return Objects.equals(this.getCreated(), that.getCreated())
                && Objects.equals(this.getText(), that.getText())
                && Objects.equals(this.getAuthor(), that.getAuthor())
                && Objects.equals(this.getRide(), that.getRide());
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (ride != null ? ride.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", created=" + created +
                ", text='" + text + '\'' +
                ", author=" + author +
                ", ride=" + ride +
                '}';
    }
}
