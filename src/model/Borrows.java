package model;

public class Borrows {
    private String borNo;
    private String docId;
    private String copyNo;
    private String bId;
    private String rId;

    public Borrows(String borNo, String docId, String copyNo, String bId, String rId) {
        this.borNo = borNo;
        this.docId = docId;
        this.copyNo = copyNo;
        this.bId = bId;
        this.rId = rId;
    }

    public String getBorNo() {
        return borNo;
    }

    public String getCopyNo() {
        return copyNo;
    }

    public String getBId() {
        return bId;
    }

    public String getRId() {
        return rId;
    }

    public String getDocId() {
        return docId;
    }
}
