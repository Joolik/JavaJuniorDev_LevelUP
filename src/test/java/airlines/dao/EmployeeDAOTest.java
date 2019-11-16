package airlines.dao;

import airlines.BaseTest;
import airlines.dao.employees.EmployeeDAOImpl;
import airlines.entities.employees.Employee;
import airlines.entities.employees.enums.EmployeePositionsEnum;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static airlines.entities.employees.enums.EmployeePositionsEnum.*;
import static org.junit.Assert.*;

public class EmployeeDAOTest extends BaseTest {

    private EmployeeDAO dao;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        dao = new EmployeeDAOImpl(manager);
    }

    @Test
    public void create() {
        Employee airhostess = new Employee(125, "Белкина Татьяна Дмитриевна", FLIGHT_ATTENDANT);
        manager.getTransaction().begin();
        try {
            dao.create(airhostess);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        assertNotNull("Not found such record in DB", manager.find(Employee.class, airhostess.getId()));
    }

    @Test
    public void update() {
        Employee airhostess = new Employee(125, "Белкина Татьяна Дмитриевна", FLIGHT_ATTENDANT);
        String newName = "Михайлова Татьяна Дмитриевна";
        EmployeePositionsEnum newPosition = PURSER;
        manager.getTransaction().begin();
        try {
            manager.persist(airhostess);
            airhostess.setName(newName).setPosition(newPosition);
            dao.update(airhostess);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        Employee newEmployee = manager.createQuery("from Employee where id = :p", Employee.class)
                .setParameter("p", airhostess.getId())
                .getSingleResult();
        assertEquals("No update for name", newName, newEmployee.getName());
        assertEquals("No update for position", newPosition, newEmployee.getPosition());
    }

    @Test
    public void delete() {
        Employee airhostess = new Employee(125, "Белкина Татьяна Дмитриевна", FLIGHT_ATTENDANT);
        manager.getTransaction().begin();
        try {
            manager.persist(airhostess);
            dao.delete(airhostess);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        assertNull("Exist record in DB", manager.find(Employee.class, airhostess.getId()));
    }

    @Ignore
    @Test
    public void findById() {
    }

    @Ignore
    @Test
    public void findByPosision() {
    }
}