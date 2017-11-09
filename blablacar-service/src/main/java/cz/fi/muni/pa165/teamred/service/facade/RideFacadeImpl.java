package cz.fi.muni.pa165.teamred.service.facade;

import cz.fi.muni.pa165.teamred.facade.RideFacade;
import cz.fi.muni.pa165.teamred.service.BeanMappingService;
import cz.fi.muni.pa165.teamred.service.RideService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

// todo
@Service
@Transactional
public class RideFacadeImpl implements RideFacade {
    final static Logger log = LoggerFactory.getLogger(RideFacadeImpl.class);

    @Inject
    private RideService rideService;

    @Autowired
    private BeanMappingService beanMappingService;
}
