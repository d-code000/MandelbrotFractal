package com.disha.app.fractal.ui;

import com.disha.app.fractal.logic.HistoryManager;
import com.disha.app.fractal.logic.PaintController;
import com.disha.app.fractal.painting.fractal.gradient.AlphaFunctions;
import com.disha.app.fractal.painting.fractal.gradient.FractalGradientPainter;
import com.disha.app.fractal.ui.menu.MenuBar;
import com.disha.converter.Converter;
import com.disha.math.fractal.MandelbrotSet;

import javax.swing.*;

public class MainWindow extends JFrame {
    
    public MainWindow() {
        setVisible(true);
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initialComponents();
    }
    
    private void initialComponents(){
        var mainPanel = new PaintPanel();
        var converter = new Converter(-2, 1, -1.5, 1.5);
        var fractalPainter = new FractalGradientPainter(converter, new MandelbrotSet(100), AlphaFunctions.SQRT);
        var historyManager = new HistoryManager(converter);
        
        new PaintController(this, mainPanel, converter, fractalPainter, historyManager);
        this.setJMenuBar(new MenuBar(historyManager, fractalPainter));
        
        this.add(mainPanel);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
