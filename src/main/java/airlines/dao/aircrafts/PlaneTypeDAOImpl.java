package airlines.dao.aircrafts;

import airlines.dao.PlaneTypeDAO;
import airlines.entities.aircrafts.PlaneType;

import javax.persistence.EntityManager;
import java.util.List;

public class PlaneTypeDAOImpl implements PlaneTypeDAO {

    private final EntityManager manager;

    public PlaneTypeDAOImpl(EntityManager manager) {
        this.manager = manager;
    }

    public void create(PlaneType planeType) {
        manager.persist(planeType);
    }

    public void update(PlaneType planeType) {
        manager.merge(planeType);
    }

    public void delete(PlaneType planeType) {
        manager.remove(planeType);
    }

    public PlaneType findById(String id) {
        return null;
    }

    public List<PlaneType> findAll() {
        return null;
    }
}
