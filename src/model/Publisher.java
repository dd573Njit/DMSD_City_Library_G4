package model;

public class Publisher {
    private String pubName;
    private String address;
    private String publisherId;

    public Publisher(String pubName, String address, String publisherId) {
        this.pubName = pubName;
        this.address = address;
        this.publisherId = publisherId;
    }

    public String getPubName() {
        return pubName;
    }

    public String getAddress() {
        return address;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }
}
