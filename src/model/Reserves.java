package model;

public class Reserves {
    private String rId;
    private int reservationNo;
    private String docId;
    private String copyNo;
    private String bId;

    public Reserves(String rId, int reservationNo, String docId, String copyNo, String bId) {
        this.rId = rId;
        this.reservationNo = reservationNo;
        this.docId = docId;
        this.copyNo = copyNo;
        this.bId = bId;
    }

    public String getRId() {
        return rId;
    }

    public String getDocId() {
        return docId;
    }

    public String getCopyNo() {
        return copyNo;
    }

    public String getBId() {
        return bId;
    }

    public int getReservationNo() {
        return reservationNo;
    }
}
