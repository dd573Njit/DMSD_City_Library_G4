package controller;

import dao.ReserveDAO;
import model.DocumentDetail;
import model.Reservation;
import model.Reserves;
import util.MessageUtil;
import util.SessionManager;
import view.ReserveView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Date;
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
        reserveView.getBtnReserve().addActionListener(e -> reserveDetail());
    }

    private void reserveDetail() {
        ReserveDAO reserveDAO = new ReserveDAO();
        String rId = SessionManager.getInstance().getCurrentReaderCardNumber();
        int resCount = reserveDAO.getReservationCountForAReader(rId);
        if(resCount >= 10) {
            MessageUtil.showErrorMessage("You already have 10 reserved docs", reserveView);
            return;
        }
        else {
            //String resId = String.format("RES0%d", resCount);
            int resId = resCount + 1;
            try {
                Reservation reservation = new Reservation(resId, new Date());
                reserveDAO.reserveNumberAndDate(reservation);
                reserveDocDetail(reserveDAO,rId,resId);
            } catch (SQLException ex) {
                reserveDocDetail(reserveDAO,rId,resId);
            }
        }
    }

    private void reserveDocDetail(ReserveDAO reserveDAO, String rId, int resId) {
        try {
            for (DocumentDetail documentDetail : documentDetail) {
                String docId = documentDetail.getDocId();
                String copyNo = documentDetail.getCopyNo();
                String bId = documentDetail.getBId();
                Reserves reserves = new Reserves(rId, resId, docId, copyNo, bId);
                reserveDAO.reserveReaderDetail(reserves);
                MessageUtil.showSuccessMessage("Successfully Reserved", reserveView);
            }
        } catch (SQLException ex) {
            MessageUtil.showErrorMessage("Document already Reserved", reserveView);
        }
    }
}
