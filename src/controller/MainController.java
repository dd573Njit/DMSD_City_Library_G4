package controller;

import dao.ReaderDAO;
import popup.AdminLoginPopup;
import popup.ReaderLoginPopup;
import util.SessionManager;
import view.MainView;

public class MainController {
    private final MainView mainView;
    private final ReaderController readerController; // Manages reader functions
    private final AdminController adminController; // Manages admin functions
    private final ReaderDAO readerDAO;

    public MainController() {
        mainView = new MainView();
        readerController = new ReaderController();
        adminController = new AdminController();
        readerDAO = new ReaderDAO();
        setupHandlers();
        mainView.setVisible(true);
    }

    private void setupHandlers() {
        mainView.getBtnReaderFunctions().addActionListener(e -> {
            if (SessionManager.getInstance().getCurrentReaderCardNumber() != null) {
                openReaderFunctions();
            } else {
                ReaderLoginPopup.showLogin(mainView, this::openReaderFunctions, readerDAO);
            }
        });
        mainView.getBtnAdminFunctions().addActionListener(e -> {
            if (SessionManager.getInstance().getAdminId() != null && SessionManager.getInstance().getAdminPassword() != null) {
                openAdminFunctions();
            }
            else {
                AdminLoginPopup.showLogin(mainView, this::openAdminFunctions);
            }
        });
        mainView.getBtnQuit().addActionListener(e -> System.exit(0));
    }

    private void openReaderFunctions() {
        readerController.showReaderView();
    }

    private void openAdminFunctions() {
        adminController.showAdminView();
    }
}


