package cz.fi.muni.pa165.teamred.service;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface TimeService {
    Date getCurrentTime();
}
