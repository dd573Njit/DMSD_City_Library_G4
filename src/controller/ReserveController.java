package controller;

import dao.ReserveDAO;
import model.DocumentDetail;
import model.Reservation;
import model.Reserves;
import util.CalendarUtil;
import util.MessageUtil;
import util.SessionManager;
import view.ReserveView;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ReserveController {
    private final ReserveView reserveView;
    private List<DocumentDetail> documentDetail;
    private boolean docsReserved = false;
    public ReserveController() {
        reserveView = new ReserveView();
        attachHandlers();
    }

    public void showReserveView(List<DocumentDetail> documentDetail) {
        this.documentDetail = documentDetail;
        docsReserved = false;
        reserveView.setVisible(true);
        reserveView.displayDocuments(documentDetail);
    }

    private void attachHandlers() {
        reserveView.getBtnReserve().addActionListener(e -> reserveDetail());
        reserveView.getBtnQuit().addActionListener(e -> closeReserveView());
    }

    private void closeReserveView() {
        reserveView.dispose();
    }

    private void reserveDetail() {
//        if(CalendarUtil.isCurrentTimeAfter6Pm()) {
//            MessageUtil.showErrorMessage("You cannot Reserve documents after 6 pm", reserveView);
//            return;
//        }
        if(documentDetail.size() > 10) {
            MessageUtil.showErrorMessage("You cannot Reserve documents with more than 10 documents", reserveView);
            return;
        }

        String rId = SessionManager.getInstance().getCurrentReaderCardNumber();
        ReserveDAO reserveDAO = new ReserveDAO();
        if(docsReserved) {
            MessageUtil.showErrorMessage("You have already reserved this document", reserveView);
            return;
        }
        int resCount = reserveDAO.getReservationCount() + 1;
        String prefix = resCount > 9 ? "RES0" : "RES00";
        String resId = prefix + resCount;
        try {
                Reservation reservation = new Reservation(resId, new Date());
                reserveDAO.reserveNumberAndDate(reservation);
                reserveDocDetail(reserveDAO,rId,resId);
            } catch (SQLException ex) {
                reserveDocDetail(reserveDAO,rId,resId);
            }
        }

    private void reserveDocDetail(ReserveDAO reserveDAO, String rId, String resId) {
        try {
            for (DocumentDetail documentDetail : documentDetail) {
                String docId = documentDetail.getDocId();
                String copyNo = documentDetail.getCopyNo();
                String bId = documentDetail.getBId();
                Reserves reserves = new Reserves(rId, resId, docId, copyNo, bId);
                reserveDAO.reserveReaderDetail(reserves);
            }
            MessageUtil.showSuccessMessage("Successfully Reserved", reserveView);
            docsReserved = true;
        } catch (SQLException ex) {
            MessageUtil.showErrorMessage("Document already Reserved", reserveView);
        }
    }
}
