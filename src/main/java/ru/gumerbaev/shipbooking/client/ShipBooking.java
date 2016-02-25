package ru.gumerbaev.shipbooking.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.vaadin.polymer.Polymer;
import com.vaadin.polymer.elemental.Function;
import com.vaadin.polymer.neon.NeonAnimatedPagesElement;
import com.vaadin.polymer.paper.PaperRippleElement;
import ru.gumerbaev.shipbooking.client.service.Services;
import ru.gumerbaev.shipbooking.client.view.Main;
import ru.gumerbaev.shipbooking.shared.EMBoat;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Айдар on 22.02.2016.
 */
public class ShipBooking implements EntryPoint {

    public void onModuleLoad() {
        // We have to load icon sets before run application
        Polymer.importHref(
                Arrays.asList(
                        "iron-icons/iron-icons.html",
                        "iron-icons/image-icons.html",
                        "iron-icons/maps-icons.html",
                        PaperRippleElement.SRC,
                        NeonAnimatedPagesElement.SRC
                ),
                new Function() {
                    public Object call(Object arg) {
                        // The app is executed when all imports succeed.
                        startApplication();
                        return null;
                    }
                });
    }

    private void startApplication() {
        final Main main = new Main();
        RootPanel.get().add(main);

        Services.data().getAllBoats(new EMAsyncCallback<List<EMBoat>>() {
            @Override
            public void onSuccess(List<EMBoat> result) {
                for (EMBoat boat : result) {
                    main.addItem(boat.getId(), boat.getName(), boat.getDescription());
                }
            }
        });
    }
}
