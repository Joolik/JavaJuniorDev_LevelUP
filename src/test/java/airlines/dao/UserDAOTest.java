package airlines.dao;

import airlines.BaseTest;
import airlines.dao.employees.UserDAOImpl;
import airlines.entities.employees.Employee;
import airlines.entities.employees.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static airlines.entities.employees.enums.EmployeePositions.CAPTAIN;
import static airlines.entities.employees.enums.EmployeePositions.MANAGER;
import static org.junit.Assert.*;

public class UserDAOTest extends BaseTest {

    private UserDAO dao;

    private Employee employee1, employee2;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        dao = new UserDAOImpl(manager);

        // заполняем базу необходимыми данными
        employee1 = new Employee(118, "Михайлов Александр Алексеевич", CAPTAIN);
        employee2 = new Employee(95, "Тулин Виктор Петрович", MANAGER);
        manager.getTransaction().begin();
        try {
            manager.persist(employee1);
            manager.persist(employee2);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
    }

    @Test
    public void create() {
        User user1 = new User().setLogin("test").setPassword("123").setEmployee(employee1).setAdmin(false);
        User user2 = new User().setLogin("sys").setPassword("123").setEmployee(employee2).setAdmin(true);
        manager.getTransaction().begin();
        try {
            dao.create(user1);
            dao.create(user2);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        User found = manager.find(User.class, user1.getLogin());
        assertNotNull("Record not found", found);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void findByLoginAndPassword() {
    }
}