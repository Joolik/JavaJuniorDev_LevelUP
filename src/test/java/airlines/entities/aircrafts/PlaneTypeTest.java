package airlines.entities.aircrafts;

import airlines.BaseTest;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class PlaneTypeTest extends BaseTest {

    @Test
    public void entityPlaneTypeTest() throws Exception {
        PlaneType planeType = new PlaneType("B737-800", "Boeing 737-800", 180);
        manager.getTransaction().begin();
        try {
            manager.persist(planeType);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        assertNotNull(manager.find(PlaneType.class, "B737-800"));

    }

}