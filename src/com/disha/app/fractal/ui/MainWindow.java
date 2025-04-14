package com.disha.app.fractal.ui;

import main.java.converter.Converter;
import com.disha.app.fractal.painting.CartesianPainter;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainWindow extends JFrame {
    private PaintPanel mainPanel;
    private CartesianPainter cartesianPainter;
    private Converter converter;
    
    public MainWindow() {
        setVisible(true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initialComponents();
    }
    
    private void initialComponents(){
        mainPanel = new PaintPanel();
        converter = new Converter(-1., 1., -1., 1., 800-40, 600-40);
        cartesianPainter = new CartesianPainter(800, 600, converter);
        
        mainPanel.setPaintAction(graphics -> {
            cartesianPainter.paint(graphics);
        });
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent event) {
                converter.setWidthPixels(mainPanel.getWidth() - 20);
                converter.setHeightPixels(mainPanel.getHeight() - 20);
            }
        });
        
        this.add(mainPanel);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
