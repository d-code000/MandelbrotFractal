package com.disha.app.fractal.ui;

import com.disha.app.fractal.painting.CartesianPainter;
import com.disha.app.fractal.painting.SetPainter;
import com.disha.converter.Converter;
import com.disha.math.fractal.MandelbrotSet;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainWindow extends JFrame {
    private PaintPanel mainPanel;
    private Converter converter;
    private CartesianPainter cartesianPainter;
    private SetPainter setPainter;
    
    
    public MainWindow() {
        setVisible(true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initialComponents();
    }
    
    private void initialComponents(){
        mainPanel = new PaintPanel();
        converter = new Converter(-2, 1, -1, 1);
        cartesianPainter = new CartesianPainter(converter);
        setPainter = new SetPainter(converter, new MandelbrotSet(100));
        
        mainPanel.setPaintAction(graphics -> {
            cartesianPainter.paint(graphics);
            setPainter.paint(graphics);
        });
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent event) {
                converter.setWidthPixels((int) Math.round(mainPanel.getWidth() * 0.95));
                converter.setHeightPixels((int) Math.round(mainPanel.getHeight() * 0.95));
            }
        });
        
        this.add(mainPanel);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
