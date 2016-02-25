package ru.gumerbaev.shipbooking.client;

/**
 * Created by Айдар on 22.02.2016.
 */
public class UIUtils {

    public static native void showError(String msg) /*-{
        $wnd.toastr.error(msg);
    }-*/;
}
