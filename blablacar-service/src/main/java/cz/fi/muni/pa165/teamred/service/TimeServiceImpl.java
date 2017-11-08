package cz.fi.muni.pa165.teamred.service;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TimeServiceImpl implements TimeService {
    @Override
    public Date getCurrentTime() {
        return new Date();
    }
}
