package ru.gumerbaev.shipbooking.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gumerbaev.shipbooking.client.service.DataService;
import ru.gumerbaev.shipbooking.server.dao.BoatDAO;
import ru.gumerbaev.shipbooking.shared.EMBoat;

import java.util.List;

/**
 * Created by Айдар on 23.02.2016.
 */
public class DataServiceImpl extends RemoteServiceServlet implements DataService {

    @Autowired
    BoatDAO boatDAO;

    @Override
    public EMBoat addBoat(EMBoat boat) {
        return boatDAO.save(boat);
    }

    @Override
    public List<EMBoat> getAllBoats() {
        return boatDAO.list();
    }

    @Override
    public void deleteBoat(Long id) {
        boatDAO.delete(id);
    }
}