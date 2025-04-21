package com.disha.app.fractal.ui;

import com.disha.app.fractal.ui.menu.file.ExitMenuItem;
import com.disha.app.fractal.ui.menu.file.OpenMenuItem;
import com.disha.app.fractal.ui.menu.file.SaveMenuItem;

import javax.swing.*;

public class FileMenu extends JMenu {
    public FileMenu() {
        super("File");
        
        this.add(new OpenMenuItem());
        this.add(new SaveMenuItem());
        this.add(new ExitMenuItem());
    }
}
