package com.disha.app.fractal.ui;

import com.disha.app.fractal.logic.PaintController;
import com.disha.converter.Converter;

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
        new PaintController(this, mainPanel, converter);
        
        this.setJMenuBar(new MenuBar());
        this.add(mainPanel);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
