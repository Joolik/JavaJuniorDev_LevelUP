package airlines.entities.employees;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class EmployeeTest {

    private EntityManagerFactory factory;
    private EntityManager manager;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        manager = factory.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        if (manager != null) {
            manager.close();
        }
        if (factory != null) {
            factory.close();
        }
    }

    @Test
    public void entityEmployeeTest() {
        Employee pilot1 = new Employee(118, "Михайлов Александр Алексеевич", "командир воздушного судна");
        Employee pilot2 = new Employee(95, "Тулин Виктор Петрович", "второй пилот");
        Employee airHostess = new Employee(156, "Свиридова Елена Дмитриевна", "старший бортпроводник");

        manager.getTransaction().begin();

        try {
            manager.persist(pilot1);
            manager.persist(pilot2);
            manager.persist(airHostess);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        assertNotNull(manager.find(Employee.class, 95));
    }

}