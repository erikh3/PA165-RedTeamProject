package cz.fi.muni.pa165.teamred.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Persistent entity Comment
 * 
 * @author miroslav.laco@gmail.com
 */
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date created;

    @NotNull
    @Column(nullable = false)
    private String text;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User author;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Ride ride;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
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
        if (!(o instanceof Comment)) return false;

        Comment comment = (Comment) o;

        if (getCreated() != null ? !getCreated().equals(comment.getCreated()) : comment.getCreated() != null)
            return false;
        if (getText() != null ? !getText().equals(comment.getText()) : comment.getText() != null) return false;
        if (getAuthor() != null ? !getAuthor().equals(comment.getAuthor()) : comment.getAuthor() != null) return false;
        return getRide() != null ? getRide().equals(comment.getRide()) : comment.getRide() == null;
    }

    @Override
    public int hashCode() {
        int result = getCreated() != null ? getCreated().hashCode() : 0;
        result = 31 * result + (getText() != null ? getText().hashCode() : 0);
        result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
        result = 31 * result + (getRide() != null ? getRide().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", created=" + created +
                ", text='" + text + '\'' +
                ", author=" + author +
                ", ride=" + ride +
                '}';
    }
}
