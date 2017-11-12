package cz.fi.muni.pa165.teamred.service;


import cz.fi.muni.pa165.teamred.entity.Ride;
import org.springframework.stereotype.Service;

import java.util.List;

// todo
@Service
public interface RideService {

    Ride createRide(Ride ride) throws IllegalArgumentException;
    void updateRide(Ride ride) throws IllegalArgumentException;
    void deleteRide(Ride ride) throws IllegalArgumentException;
    Ride findById(Long id) throws IllegalArgumentException;
    List<Ride> findAll();
}
