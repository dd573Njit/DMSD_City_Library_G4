package controller;

import view.MainView;

public class MainController {
    private MainView mainView;
    private ReaderController readerController;

    public MainController() {
        mainView = new MainView();
        readerController = new ReaderController();
        mainView.getBtnReaderFunctions().addActionListener(e -> readerController.showReaderView());
        mainView.setVisible(true);
    }
}

