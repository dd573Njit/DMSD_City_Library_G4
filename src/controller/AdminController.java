package controller;

import util.SessionManager;
import view.AdminView;

public class AdminController {
    private final AdminView adminView;
    public AdminController() {
        adminView = new AdminView();
        attachHandlers();
    }

    public void showAdminView() {
        adminView.setVisible(true);
    }

    private void attachHandlers() {
        adminView.getBtnLogout().addActionListener(e -> logoutHandler());
    }

    private void logoutHandler() {
        SessionManager.getInstance().setAdminId(null);
        SessionManager.getInstance().setAdminPassword(null);
        adminView.dispose();
    }
}
