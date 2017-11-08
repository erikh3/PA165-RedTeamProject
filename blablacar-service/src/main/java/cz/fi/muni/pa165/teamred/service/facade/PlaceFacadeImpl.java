package cz.fi.muni.pa165.teamred.service.facade;

import cz.fi.muni.pa165.teamred.facade.PlaceFacade;
import cz.fi.muni.pa165.teamred.service.BeanMappingService;
import cz.fi.muni.pa165.teamred.service.PlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

// todo
@Service
@Transactional
public class PlaceFacadeImpl implements PlaceFacade {
    final static Logger log = LoggerFactory.getLogger(PlaceFacadeImpl.class);

    @Inject
    private PlaceService placeService;

    @Autowired
    private BeanMappingService beanMappingService;
}
