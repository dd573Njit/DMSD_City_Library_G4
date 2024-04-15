package model;

public class Branch {
    private String bId;
    private String lName;
    private String location;

    public Branch(String bId, String lName, String location) {
        this.bId = bId;
        this.lName = lName;
        this.location = location;
    }

    public String getBId() {
        return bId;
    }

    public String getLName() {
        return lName;
    }

    public String getLocation() {
        return location;
    }
}
