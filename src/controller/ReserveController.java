package controller;

import model.DocumentDetail;
import view.ReaderView;
import view.ReserveView;

import java.util.List;

public class ReserveController {
    private final ReserveView reserveView;
    private List<DocumentDetail> documentDetail;
    public ReserveController() {
        reserveView = new ReserveView();
        attachHandlers();
    }

    public void showReserveView(List<DocumentDetail> documentDetail) {
        this.documentDetail = documentDetail;
        reserveView.setVisible(true);
        reserveView.displayDocuments(documentDetail);
    }

    private void attachHandlers() {
        reserveView.getBtnReserve().addActionListener(e -> reserveDocDetail());
    }

    private void reserveDocDetail() {
        for (DocumentDetail documentDetail1 : documentDetail) {
            System.out.println(documentDetail1.getTitle());
        }
    }
}
