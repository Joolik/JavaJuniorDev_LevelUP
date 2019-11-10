package airlines.entities.aircrafts;

import airlines.entities.BaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class PassengerPlaneTest extends BaseTest {

    @Test
    public void entityPlaneTest() throws Exception {
        PlaneType planeType1 = new PlaneType("B737-800", "Boeing 737-800", 180);
        PlaneType planeType2 = new PlaneType("SSJ-100", "Sukhoi Superjet 100-95B", 87);
        PlaneType planeType3 = new PlaneType("B777-300ER", "Boeing 777-300ER", 402);
        PlaneType planeType4 = new PlaneType("A330-300/295", "Airbus 330-300", 295);
        PlaneType planeType5 = new PlaneType("A330-300/335", "Airbus 330-300", 335);
        PlaneType planeType6 = new PlaneType("A330-300/440", "Airbus 330-300", 440);


        PassengerPlane plane1 = new PassengerPlane("VP-BIY", planeType1);
        PassengerPlane plane2 = new PassengerPlane("RA-89053", planeType2);
        PassengerPlane plane3 = new PassengerPlane("RA-89102", planeType2);
        PassengerPlane plane4 = new PassengerPlane("VP-BGT", planeType3);
        PassengerPlane plane5 = new PassengerPlane("VQ-BEG", planeType4);
        PassengerPlane plane6 = new PassengerPlane("VQ-BNL", planeType5);
        PassengerPlane plane7 = new PassengerPlane("VQ-BNF", planeType6);
        PassengerPlane plane8 = new PassengerPlane("VQ-BND", planeType5);
        manager.getTransaction().begin();

        try {
            manager.persist(planeType1);
            manager.persist(planeType6);

            manager.persist(plane1);
            manager.persist(plane7);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        assertNotNull(manager.find(PassengerPlane.class, "VP-BIY"));

    }
}