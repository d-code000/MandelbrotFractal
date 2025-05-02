package com.disha.app.fractal.ui.menu;

import com.disha.app.fractal.logic.HistoryManager;
import com.disha.app.fractal.painting.fractal.FractalPainter;
import com.disha.app.fractal.ui.menu.file.ExitMenuItem;
import com.disha.app.fractal.ui.menu.file.OpenMenuItem;
import com.disha.app.fractal.ui.menu.file.SaveMenuItem;

import javax.swing.*;

public class FileMenu extends JMenu {
    public FileMenu(HistoryManager historyManager, FractalPainter fractalPainter) {
        super("File");

        this.add(new OpenMenuItem(historyManager));
        this.add(new SaveMenuItem(historyManager, fractalPainter));
        this.add(new ExitMenuItem());
    }
}
