package cz.fi.muni.pa165.teamred.dto;

import java.util.Date;
import java.util.Objects;

/**
 * @author Erik Horváth
 */
public class CommentDTO {
    private Long id;
    private Date created;
    private String text;
    private Long authorId;
    private Long rideId;

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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long userId) {
        this.authorId = userId;
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

        CommentDTO that = (CommentDTO) o;

        return Objects.equals(this.getCreated(), that.getCreated())
                && Objects.equals(this.getText(), that.getText())
                && Objects.equals(this.getAuthorId(), that.getAuthorId())
                && Objects.equals(this.getRideId(), that.getRideId());
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (authorId != null ? authorId.hashCode() : 0);
        result = 31 * result + (rideId != null ? rideId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", created=" + created +
                ", text='" + text + '\'' +
                ", authorId=" + authorId +
                ", rideId=" + rideId +
                '}';
    }
}
