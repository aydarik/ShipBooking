package ru.gumerbaev.shipbooking.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ru.gumerbaev.shipbooking.client.EMAsyncCallback;
import ru.gumerbaev.shipbooking.client.service.Services;
import ru.gumerbaev.shipbooking.client.view.component.Item;
import ru.gumerbaev.shipbooking.client.view.dialog.AddItemDialog;
import ru.gumerbaev.shipbooking.client.view.dialog.DeleteItemsDialog;

/**
 * Created by Айдар on 22.02.2016.
 */
public class Main extends Composite {
    interface MainUiBinder extends UiBinder<HTMLPanel, Main> {
    }

    private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);

    @UiField
    HTMLPanel content;

    @UiField
    AddItemDialog addItemDialog;
    @UiField
    DeleteItemsDialog deleteItemsDialog;

    public Main() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("addButton")
    protected void onAddButtonClick(ClickEvent e) {
        addItemDialog.open(this);
    }

    public void addItem(Long id, String title, String description) {
        Item item = new Item(id);
        item.setTitle(title);
        item.setDescription(description);
        content.add(item);
    }

/*
    private void closeMenu() {
        if (drawerPanel.getNarrow()) {
            drawerPanel.closeDrawer();
        }
    }
*/

    @UiHandler("deleteButton")
    protected void onDeleteButtonClick(ClickEvent e) {
        deleteItemsDialog.open(this);
    }

    public void deleteItems() {
        for (int i = content.getWidgetCount() - 1; i > -1; i--) {
            final Item item = (Item) content.getWidget(i);
            if (item.isDone()) {
                Services.data().deleteBoat(item.getId(), new EMAsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        content.remove(item);
                    }
                });
            }
        }
    }

    public boolean hasSelectedItems() {
        int counter = 0;
        for (int i = content.getWidgetCount() - 1; i > -1; i--) {
            Item item = (Item) content.getWidget(i);
            if (item.isDone()) {
                counter++;
            }
        }
        return counter > 0;
    }
}