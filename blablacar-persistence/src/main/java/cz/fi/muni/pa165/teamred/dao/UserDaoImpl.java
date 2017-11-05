package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Jozef Cibík on 25.10.2017.
 */
@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }
        entityManager.merge(user);
    }

    @Override
    public void delete(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }

    @Override
    public User findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User id is null.");
        }
        return entityManager.find(User.class,id);
    }

    @Override
    public User findByNickname(String nickname) {
        if (nickname == null) {
            throw new IllegalArgumentException("User nickname is null.");
        }
        try {
            return entityManager
                    .createQuery
                            ("select u from User c where nickname =:nick",
                                    User.class)
                    .setParameter("nick", nickname)
                    .getSingleResult();
        } catch (NoResultException ex) {
            //Print Exception Message
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

}
