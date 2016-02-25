package ru.gumerbaev.shipbooking.client.view.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.vaadin.polymer.paper.widget.PaperDialog;
import ru.gumerbaev.shipbooking.client.UIUtils;
import ru.gumerbaev.shipbooking.client.view.Main;

/**
 * Created by Айдар on 22.02.2016.
 */
public class DeleteItemsDialog extends Composite {
    interface DeleteItemsDialogUiBinder extends UiBinder<HTMLPanel, DeleteItemsDialog> {
    }

    private static DeleteItemsDialogUiBinder uiBinder = GWT.create(DeleteItemsDialogUiBinder.class);

    @UiField
    PaperDialog deleteItemsDialog;

    private Main parentView;

    public DeleteItemsDialog() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("confirmDeleteButton")
    protected void onConfirmAddButtonClick(ClickEvent e) {
        parentView.deleteItems();
    }

    public void open(Main parentView) {
        this.parentView = parentView;

        if (parentView.hasSelectedItems()) {
            deleteItemsDialog.open();
        } else {
            UIUtils.showError("Не выбрано ни одного элемента!");
        }
    }
}