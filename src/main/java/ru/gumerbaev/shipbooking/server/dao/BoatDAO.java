package ru.gumerbaev.shipbooking.server.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gumerbaev.shipbooking.shared.EMBoat;

import java.util.List;

/**
 * Created by agumerbaev on 24.02.2016.
 */
@Service("boatDAO")
@Transactional
public class BoatDAO {

    @Autowired
    protected SessionFactory sessionFactory;

    public EMBoat getById(Long id) {
        return sessionFactory.getCurrentSession().get(EMBoat.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<EMBoat> list() {
        return sessionFactory.getCurrentSession().createCriteria(EMBoat.class).list();
    }

    public EMBoat save(EMBoat entity) {
        return (EMBoat) sessionFactory.getCurrentSession().save(entity);
    }

    public void delete(Long id) {
        sessionFactory.getCurrentSession().delete(getById(id));
    }

    public void delete(EMBoat entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }
}
