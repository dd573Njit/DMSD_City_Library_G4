package model;

public class Copy {
    private String docId;
    private String copyNo;
    private String bId;
    private String position;

    public Copy(String docId, String copyNo, String bId, String position) {
        this.docId = docId;
        this.copyNo = copyNo;
        this.bId = bId;
        this.position = position;
    }

    public String getDocId() {
        return docId;
    }

    public String getBId() {
        return bId;
    }

    public String getCopyNo() {
        return copyNo;
    }

    public String getPosition() {
        return position;
    }
}
