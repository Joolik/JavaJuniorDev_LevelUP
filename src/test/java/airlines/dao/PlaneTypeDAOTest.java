package airlines.dao;

import airlines.dao.aircrafts.PlaneTypeDAOImpl;
import airlines.BaseTest;
import airlines.entities.aircrafts.PlaneType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlaneTypeDAOTest extends BaseTest {

    private PlaneTypeDAO dao;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        dao = new PlaneTypeDAOImpl(manager);
    }

    @Test
    public void create() {
        PlaneType planeType = new PlaneType("B737-800", "Boeing 737-800", 180);
        manager.getTransaction().begin();
        try {
            dao.create(planeType);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        assertNotNull("Not found such record in DB", manager.find(PlaneType.class, planeType.getId()));
    }

    @Test
    public void update() {
        PlaneType planeType = new PlaneType("B737-800", "Boeing 737-800", 180);
        String newName = "Новое имя";
        int newSeatingCapacity = 97;
        manager.getTransaction().begin();
        try {
            manager.persist(planeType);
            planeType.setName(newName)
                    .setSeatingCapacity(newSeatingCapacity);
            dao.update(planeType);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        PlaneType newPlaneType = manager.createQuery("from PlaneType where id = :p", PlaneType.class)
                .setParameter("p", "B737-800")
                .getSingleResult();
        assertEquals("No update for name", newName, newPlaneType.getName());
        assertEquals("No update for seatingCapacity", newSeatingCapacity, newPlaneType.getSeatingCapacity());
    }

    @Test
    public void delete() {
        PlaneType planeType = new PlaneType("B737-800", "Boeing 737-800", 180);
        manager.getTransaction().begin();
        try {
            manager.persist(planeType);
            dao.delete(planeType);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        assertNull("Exist record in DB", manager.find(PlaneType.class, planeType.getId()));
    }

    @Ignore
    @Test
    public void findById() {
    }

    @Ignore
    @Test
    public void findAll() {
    }
}