package com.disha.app.fractal.ui.menu.file;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitMenuItem extends JMenuItem implements ActionListener {
    public ExitMenuItem() {
        super("Exit");
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
