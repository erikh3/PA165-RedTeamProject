package cz.fi.muni.pa165.teamred.dto;

import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Ride;

import java.util.Date;
import java.util.Objects;

/**
 * @author miroslav.laco@gmail.com
 */
public class CommentAuthorDTO {
    private Long id;
    private Date created;
    private String text;
    private UserDTO author;
    private Long rideId;

    public CommentAuthorDTO() {}

    public CommentAuthorDTO(CommentDTO comment) {
        this.id = comment.getId();
        this.created = comment.getCreated();
        this.text = comment.getText();
        this.rideId = comment.getRideId();
    }

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

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO user) {
        this.author = user;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentAuthorDTO that = (CommentAuthorDTO) o;

        return Objects.equals(this.getCreated(), that.getCreated())
                && Objects.equals(this.getText(), that.getText())
                && Objects.equals(this.getAuthor(), that.getAuthor())
                && Objects.equals(this.getRideId(), that.getRideId());
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (rideId != null ? rideId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", created=" + created +
                ", text='" + text + '\'' +
                ", author=" + author +
                ", rideId=" + rideId +
                '}';
    }
}
