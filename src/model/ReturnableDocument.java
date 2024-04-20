package model;

import util.CalendarUtil;

import java.util.Date;

public class ReturnableDocument {
    private String docId;
    private String title;
    private String copyNo;
    private Date bDTime;
    private Date rDTime;

    public ReturnableDocument(String docId, String title, String copyNo, Date bDTime, Date rDTime) {
        this.docId = docId;
        this.title = title;
        this.copyNo = copyNo;
        this.bDTime = bDTime;
        this.rDTime = rDTime;
    }

    public String getDocId() {
        return docId;
    }

    public String getTitle() {
        return title;
    }

    public String getCopyNo() {
        return copyNo;
    }

    public Date getBDTime() {
        return bDTime;
    }

    public Date getRDTime() {
        return rDTime;
    }

    @Override
    public String toString() {
        return "Title: " + title + copyNo + "\t\tBorrowed Date: " + CalendarUtil.getStringFormattedDate(bDTime) +
                "\t\tReturned Date: " + CalendarUtil.getStringFormattedDate(rDTime);
    }
}
