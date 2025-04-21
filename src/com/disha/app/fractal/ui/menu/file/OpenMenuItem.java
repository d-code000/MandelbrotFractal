package com.disha.app.fractal.ui.menu.file;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenMenuItem extends JMenuItem implements ActionListener {
    public OpenMenuItem() {
        super("Open");
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Todo: реализовать открытие файла
    }
}
