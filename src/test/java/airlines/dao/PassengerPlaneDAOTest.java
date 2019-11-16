package airlines.dao;

import airlines.BaseTest;
import airlines.dao.PassengerPlaneDAO;
import airlines.dao.aircrafts.PassengerPlaneDAOImpl;
import airlines.entities.aircrafts.PassengerPlane;
import airlines.entities.aircrafts.PlaneType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class PassengerPlaneDAOTest extends BaseTest {

    private PassengerPlaneDAO dao;
    private PlaneType planeType;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        dao = new PassengerPlaneDAOImpl(manager);
        planeType = new PlaneType("B737-800", "Boeing 737-800", 180);
    }

    @Test
    public void create() {
        PassengerPlane plane = new PassengerPlane("VP-BIY", planeType);

        manager.getTransaction().begin();
        try {
            manager.persist(planeType);
            dao.create(plane);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        assertNotNull("Not found such record in DB",
                manager.find(PassengerPlane.class, "VP-BIY"));
    }

    @Test
    public void updateType() throws ParseException {
        PassengerPlane plane = new PassengerPlane("VP-BIY", planeType);

        PlaneType newPlaneType = new PlaneType("SSJ-100", "Sukhoi Superjet 100-95B", 87);
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date date = format.parse("2020-04-15");

        manager.getTransaction().begin();
        try {
            manager.persist(planeType);
            manager.persist(newPlaneType);
            manager.persist(plane);

            plane.setType(newPlaneType).setLastServiceDate(date);
            dao.update(plane);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

       PassengerPlane newPassengerPlane = manager.createQuery("from PassengerPlane where plainRegNumber = :p", PassengerPlane.class)
                .setParameter("p", "VP-BIY")
                .getSingleResult();

        assertEquals("No update for type", newPlaneType, newPassengerPlane.getType());
        assertEquals("No update for date", date, newPassengerPlane.getLastServiceDate());

    }

    @Test
    public void delete() {
        PassengerPlane plane = new PassengerPlane("VP-BIY", planeType);

        manager.getTransaction().begin();
        try {
            manager.persist(planeType);
            manager.persist(plane);
            dao.delete(plane);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        assertNull("Exist record in DB", manager.find(PassengerPlane.class, plane.getPlainRegNumber()));
        assertNotNull("Not found such record in DB", manager.find(PlaneType.class, planeType.getId()));
    }

    @Ignore
    @Test
    public void findById() {
    }

    @Ignore
    @Test
    public void findByType() {
    }

    @Ignore
    @Test
    public void findAll() {
    }
}