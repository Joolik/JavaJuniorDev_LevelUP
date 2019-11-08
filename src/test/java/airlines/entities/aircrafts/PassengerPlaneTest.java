package airlines.entities.aircrafts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class PassengerPlaneTest {

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
    public void entityPlaneTest() throws Exception {
        PassengerPlane plane = new PassengerPlane("PL5078", "Boeing 737-800", 180);
        manager.getTransaction().begin();

        try {
            manager.persist(plane);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        assertNotNull(manager.find(PassengerPlane.class, "PL5078"));

    }
}