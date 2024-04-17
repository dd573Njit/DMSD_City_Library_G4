package controller;

import model.DocumentDetail;
import view.CheckoutView;

import java.util.List;

public class CheckoutController {
    private final CheckoutView checkoutView;
    public CheckoutController() {
        checkoutView = new CheckoutView();
    }

    public void showCheckoutView(List<DocumentDetail> documents) {
        checkoutView.setVisible(true);
    }
}
