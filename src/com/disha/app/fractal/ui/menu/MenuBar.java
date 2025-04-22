package com.disha.app.fractal.ui.menu;

import com.disha.app.fractal.logic.HistoryManager;
import com.disha.app.fractal.painting.fractal.FractalPainter;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar(HistoryManager historyManager, FractalPainter fractalPainter) {
        super();
        
        this.add(new FileMenu(historyManager, fractalPainter));
    }
}
