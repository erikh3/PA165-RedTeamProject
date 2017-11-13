package cz.fi.muni.pa165.teamred.service;


import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

// todo
@Service
public interface RideService {

    Ride createRide(Ride ride) throws IllegalArgumentException;
    void updateRide(Ride ride) throws IllegalArgumentException;
    void deleteRide(Ride ride) throws IllegalArgumentException;

    void addPassenger(Ride ride, User passenger) throws IllegalArgumentException;
    void addComment(Ride ride, Comment comment);
    void removePassenger(Ride ride, User passenger);
    void removeComment(Ride ride, Comment comment);

    Ride findById(Long id) throws IllegalArgumentException;
    List<Ride> findAll();
}
