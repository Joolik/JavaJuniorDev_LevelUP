package airlines.entities;

import org.junit.After;
import org.junit.Before;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class BaseTest {

    protected EntityManagerFactory factory;
    protected EntityManager manager;

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
}
