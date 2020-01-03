package airlines.dao.employees;

import airlines.dao.UserDAO;
import airlines.entities.employees.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class UserDAOImpl implements UserDAO {

    private final EntityManager manager;

    @Autowired
    public UserDAOImpl(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void create(User user) {
        manager.persist(user);
    }

    @Override
    public void update(User user) {
        manager.merge(user);
    }

    @Override
    public void delete(User user) {
        manager.remove(user);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        return manager.createQuery("from User where login = :l AND password = :p", User.class)
                .setParameter("l", login)
                .setParameter("p", password)
                .getSingleResult();
    }
}
