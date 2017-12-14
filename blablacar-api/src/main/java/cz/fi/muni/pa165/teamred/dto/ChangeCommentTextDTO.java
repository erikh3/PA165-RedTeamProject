package cz.fi.muni.pa165.teamred.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Erik Horváth
 */
@SuppressWarnings("unused")
public class ChangeCommentTextDTO {
    @NotNull
    private Long commentId;

    @NotNull
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChangeCommentTextDTO that = (ChangeCommentTextDTO) o;

        if (commentId != null ? !commentId.equals(that.commentId) : that.commentId != null) return false;
        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        int result = commentId != null ? commentId.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChangeCommentTextDTO{" +
                "commentId=" + commentId +
                ", text='" + text + '\'' +
                '}';
    }
}
