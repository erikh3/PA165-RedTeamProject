package cz.fi.muni.pa165.teamred.testutils;

import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Erik Horváth
 */
@SuppressWarnings("WeakerAccess")
public class CommentFactory {
    public static Comment createComment(String text, Date created) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setCreated(created);
        return comment;
    }

    public static Comment createComment(String text, Date created, User author) {
        Comment comment = createComment(text, created);
        comment.setAuthor(author);
        author.addComment(comment);
        return comment;
    }

    public static Comment createComment(String text, Date created, User author, Ride ride) {
        Comment comment = createComment(text, created, author);
        comment.setRide(ride);
        ride.addComment(comment);
        return comment;
    }

    public static Comment createPositiveComment() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.MARCH, 15);
        return createComment("Very good ride, enjoyed every single bit.", calendar.getTime());
    }

    public static Comment createPositiveComment(User author) {
        return createPositiveComment(author, null);
    }

    public static Comment createPositiveComment(Ride ride) {
        return createPositiveComment(null, ride);
    }

    public static Comment createPositiveComment(User author, Ride ride) {
        Comment comment = createPositiveComment();

        if (author != null) {
            comment.setAuthor(author);
            author.addComment(comment);
        }

        if (ride != null) {
            comment.setRide(ride);
            ride.addComment(comment);
        }

        return comment;
    }

    public static Comment createNegativeComment() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.DECEMBER, 2);
        return createComment("Never again.", calendar.getTime());
    }

    public static Comment createNegativeComment(User author) {
        return createNegativeComment(author, null);
    }

    public static Comment createNegativeComment(Ride ride) {
        return createNegativeComment(null, ride);
    }

    public static Comment createNegativeComment(User author, Ride ride) {
        Comment comment = createNegativeComment();

        if (author != null) {
            comment.setAuthor(author);
            author.addComment(comment);
        }

        if (ride != null) {
            comment.setRide(ride);
            ride.addComment(comment);
        }

        return comment;
    }
}
