package model;

public class Reader {
    private String rID;
    private String rName;
    private String rType;
    private String rAddress;
    private int rPhone_No;

    public Reader(String rID, String rName, String rType, String rAddress, int rPhone_No) {
        this.rID = rID;
        this.rName = rName;
        this.rType = rType;
        this.rAddress = rAddress;
        this.rPhone_No = rPhone_No;
    }

    public String getRID() {
        return rID;
    }

    public String getRName() {
        return rName;
    }

    public String getRType() {
        return rType;
    }

    public String getRAddress() {
        return rAddress;
    }

    public int getRPhone_No() {
        return rPhone_No;
    }
}
