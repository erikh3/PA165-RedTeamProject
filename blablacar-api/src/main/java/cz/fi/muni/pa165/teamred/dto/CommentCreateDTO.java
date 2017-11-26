package cz.fi.muni.pa165.teamred.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author Erik Horváth
 */
@SuppressWarnings("WeakerAccess")
public class CommentCreateDTO {
    @NotNull
    @Size(min = 1, max = 2048)
    private String text;

    @NotNull
    private Long rideId;

    @NotNull
    private Long authorId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentCreateDTO)) return false;

        CommentCreateDTO that = (CommentCreateDTO) o;

        return Objects.equals(text, that.getText())
                && Objects.equals(rideId, that.getRideId())
                && Objects.equals(authorId, that.getAuthorId());
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (rideId != null ? rideId.hashCode() : 0);
        result = 31 * result + (authorId != null ? authorId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommentCreateDTO{" +
                "text='" + text + '\'' +
                ", rideId=" + rideId +
                ", authorId=" + authorId +
                '}';
    }
}
