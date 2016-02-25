package ru.gumerbaev.shipbooking.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by Айдар on 24.02.2016.
 */
public abstract class EMAsyncCallback<E> implements AsyncCallback<E> {

    @Override
    public void onFailure(Throwable caught) {
        UIUtils.showError(caught.getLocalizedMessage());
    }
}
