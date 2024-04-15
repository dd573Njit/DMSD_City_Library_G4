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

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPDate(Date pDate) {
        this.pDate = pDate;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }
}

