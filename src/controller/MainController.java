package controller;

import dao.ReaderDAO;
import popup.AdminLoginPopup;
import popup.ReaderLoginPopup;
import view.MainView;

public class MainController {
    private MainView mainView;
    private ReaderController readerController; // Manages reader functions
    private AdminController adminController; // Manages admin functions
    private ReaderDAO readerDAO;

    public MainController() {
        mainView = new MainView();
        readerController = new ReaderController();
        adminController = new AdminController();
        readerDAO = new ReaderDAO();
        setupHandlers();
        mainView.setVisible(true);
    }

    private void setupHandlers() {
        mainView.getBtnReaderFunctions().addActionListener(e -> ReaderLoginPopup.showLogin(mainView, this::openReaderFunctions, readerDAO));
        mainView.getBtnAdminFunctions().addActionListener(e -> AdminLoginPopup.showLogin(mainView, this::openAdminFunctions));
    }

    private void openReaderFunctions() {
        readerController.showReaderView();
    }

    private void openAdminFunctions() {
        adminController.showAdminView();
    }
}


