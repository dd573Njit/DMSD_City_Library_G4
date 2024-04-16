package model;

public class Publisher {
    private String pubName;
    private String address;
    private String publisherId;

    public Publisher(String publisherId, String pubName, String address) {
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
}
