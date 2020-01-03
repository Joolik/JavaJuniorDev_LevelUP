package airlines.dao.aircrafts;

import airlines.dao.PassengerPlaneDAO;
import airlines.entities.aircrafts.PassengerPlane;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public class PassengerPlaneDAOImpl implements PassengerPlaneDAO {

    private final EntityManager manager;

    @Autowired
    public PassengerPlaneDAOImpl(EntityManager manager) {
        this.manager = manager;
    }

    public void create(PassengerPlane plane) {
        manager.persist(plane);
    }

    public void update(PassengerPlane plane) {
        manager.merge(plane);
    }

    public void delete(PassengerPlane plane) {
        manager.remove(plane);
    }

    public PassengerPlane findById(String plainRegNumber) {
        return null;
    }

    public List<PassengerPlane> findByType(String plainType) {
        return null;
    }

    public List<PassengerPlane> findAll() {
        return null;
    }
}
