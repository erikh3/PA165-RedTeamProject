package cz.fi.muni.pa165.teamred.service.facade;

import cz.fi.muni.pa165.teamred.facade.UserFacade;
import cz.fi.muni.pa165.teamred.service.BeanMappingService;
import cz.fi.muni.pa165.teamred.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

// todo
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {
    final static Logger log = LoggerFactory.getLogger(UserFacadeImpl.class);

    @Inject
    private UserService userService;

    @Autowired
    private BeanMappingService beanMappingService;
}
