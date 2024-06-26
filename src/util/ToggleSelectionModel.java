package util;

import javax.swing.*;

public class ToggleSelectionModel extends DefaultListSelectionModel {
    @Override
    public void setSelectionInterval(int index0, int index1) {
        if(super.isSelectedIndex(index0)) {
            super.removeSelectionInterval(index0, index1);
        } else {
            super.addSelectionInterval(index0, index1);
        }
    }
}

