package ru.gumerbaev.shipbooking.client.service;

import com.google.gwt.core.client.GWT;

/**
 * Created by Айдар on 24.02.2016.
 */
public class Services {
    private final static DataServiceAsync dataService = GWT.create(DataService.class);

    public static DataServiceAsync data() {
        return dataService;
    }
}
