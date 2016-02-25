package ru.gumerbaev.shipbooking.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ru.gumerbaev.shipbooking.shared.EMBoat;

import java.util.List;

/**
 * Created by Айдар on 23.02.2016.
 */
@RemoteServiceRelativePath("rpc/data")
public interface DataService extends RemoteService {

    EMBoat addBoat(EMBoat boat);

    List<EMBoat> getAllBoats();

    void deleteBoat(Long id);
}
