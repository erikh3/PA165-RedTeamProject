package cz.fi.muni.pa165.teamred.service.config;

import cz.fi.muni.pa165.teamred.entity.User;
import cz.fi.muni.pa165.teamred.service.UserService;
import org.dozer.DozerConverter;

import javax.inject.Inject;

/**
 * @author Erik Horváth
 */
public class UserIdConverter extends DozerConverter<User, Long> {
    @Inject
    private UserService userService;

    public UserIdConverter() {
        super(User.class, Long.class);
    }

    @Override
    public Long convertTo(User user, Long id) {
        return user == null ? null : user.getId();
    }

    @Override
    public User convertFrom(Long id, User user) {
        return id == null ? null : userService.findUserById(id);
    }
}
