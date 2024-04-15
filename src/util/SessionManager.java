package util;

public class SessionManager {
    private static SessionManager instance;
    private String currentReaderCardNumber;
    private String adminId;
    private String adminPassword;

    private SessionManager() {
        // Private constructor to prevent instantiation
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public String getCurrentReaderCardNumber() {
        return currentReaderCardNumber;
    }

    public void setCurrentReaderCardNumber(String cardNumber) {
        this.currentReaderCardNumber = cardNumber;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}

