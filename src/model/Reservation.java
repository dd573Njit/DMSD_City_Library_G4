package model;

import java.util.Date;

public class Reservation {
    private int resNo;
    private Date dTime;

    public Reservation(int resNo, Date dTime) {
        this.resNo = resNo;
        this.dTime = dTime;
    }

    public int getResNo() {
        return resNo;
    }

    public Date getDTime() {
        return dTime;
    }
}
