package model;

import java.util.Date;

public class Document {
    private String docId;
    private String title;
    private Date pDate;
    private String publisherId;

    public Document(String docId, String title, Date pDate, String publisherId) {
        this.docId = docId;
        this.title = title;
        this.pDate = pDate;
        this.publisherId = publisherId;
    }

    public String getDocId() {
        return docId;
    }

    public String getTitle() {
        return title;
    }

    public Date getPDate() {
        return pDate;
    }

    public String getPublisherId() {
        return publisherId;
    }

    @Override
    public String toString() {
        return title;  // This will be used by JList for display
    }
}