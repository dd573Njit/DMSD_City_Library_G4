package model;

import java.util.Date;

public class Reservation {
    private String resNo;
    private Date dTime;

    public Reservation(String resNo, Date dTime) {
        this.resNo = resNo;
        this.dTime = dTime;
    }

    public String getResNo() {
        return resNo;
    }

    public Date getDTime() {
        return dTime;
    }
}
