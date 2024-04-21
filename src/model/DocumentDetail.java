package model;

public class DocumentDetail {
    private String docId;
    private String title;
    private String copyNo;
    private String bId;

    public DocumentDetail(String docId, String title, String copyNo, String bId) {
        this.docId = docId;
        this.title = title;
        this.copyNo = copyNo;
        this.bId = bId;
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

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "DOCID: " + docId + "\t\t TITLE: " + title + "\t\t COPY NUMBER: " + copyNo;  // This will be used by JList for display
    }
}
