package com.disha.app.fractal.ui.menu.file;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveMenuItem extends JMenuItem implements ActionListener {
    public SaveMenuItem() {
        super("Save");
        
        addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e) {
        // Todo: реализовать сохранение в файл
    }
}
