package model;

import java.util.Date;

public class Borrowing {
    private String borNo;
    private Date bDTime;
    private Date rDTime;

    public Borrowing(String borNo, Date bDTime, Date rDTime) {
        this.borNo = borNo;
        this.bDTime = bDTime;
        this.rDTime = rDTime;
    }

    public String getBorNo() {
        return borNo;
    }

    public Date getBDTime() {
        return bDTime;
    }

    public Date getRDTime() {
        return rDTime;
    }
}
