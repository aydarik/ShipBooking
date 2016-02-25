package ru.gumerbaev.shipbooking.client.view.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.vaadin.polymer.paper.widget.PaperDialog;
import com.vaadin.polymer.paper.widget.PaperInput;
import com.vaadin.polymer.paper.widget.PaperTextarea;
import ru.gumerbaev.shipbooking.client.EMAsyncCallback;
import ru.gumerbaev.shipbooking.client.service.Services;
import ru.gumerbaev.shipbooking.client.view.Main;
import ru.gumerbaev.shipbooking.shared.EMBoat;

/**
 * Created by Айдар on 22.02.2016.
 */
public class AddItemDialog extends Composite {
    interface AddItemDialogUiBinder extends UiBinder<HTMLPanel, AddItemDialog> {
    }

    private static AddItemDialogUiBinder uiBinder = GWT.create(AddItemDialogUiBinder.class);

    @UiField
    PaperDialog addItemDialog;
    @UiField
    PaperInput titleInput;
    @UiField
    PaperTextarea descriptionInput;

    private Main parentView;

    public AddItemDialog() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("confirmAddButton")
    protected void onConfirmAddButtonClick(ClickEvent e) {
        if (!titleInput.getValue().isEmpty()) {
            EMBoat boat = new EMBoat();
            boat.setName(titleInput.getValue());
            boat.setDescription(descriptionInput.getValue());
            Services.data().addBoat(boat, new EMAsyncCallback<EMBoat>() {
                @Override
                public void onSuccess(EMBoat result) {
                    parentView.addItem(result.getId(), titleInput.getValue(), descriptionInput.getValue());
                }
            });
        }
    }

    public void open(Main parentView) {
        this.parentView = parentView;

        // clear text fields
        titleInput.setRequired(false);
        titleInput.setValue("");
        titleInput.setRequired(true);

        descriptionInput.setValue("");

        addItemDialog.open();
    }
}