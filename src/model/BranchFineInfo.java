package model;

public class BranchFineInfo {
    private String bId;
    private String lName;
    private double avg_fine;

    public BranchFineInfo(String bId, String lName, double avg_fine) {
        this.bId = bId;
        this.lName = lName;
        this.avg_fine = avg_fine;
    }

    public String getBId() {
        return bId;
    }

    public double getAvg_fine() {
        return avg_fine;
    }

    public String getLName() {
        return lName;
    }

    @Override
    public String toString() {
        return "BranchId: " + bId + "\t\t Branch Name: " + lName + "\t\t Avg fine: " + avg_fine;
    }
}
