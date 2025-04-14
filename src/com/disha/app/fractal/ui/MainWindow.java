package com.disha.app.fractal.ui;

import com.disha.app.fractal.painting.fractal.FractalPainter;
import com.disha.app.fractal.painting.fractal.gradient.AlphaFunctions;
import com.disha.app.fractal.painting.fractal.gradient.FractalGradientPainter;
import com.disha.converter.Converter;
import com.disha.math.fractal.MandelbrotSet;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainWindow extends JFrame {
    private PaintPanel mainPanel;
    private Converter converter;
    private FractalPainter setPainter;
    
    
    public MainWindow() {
        setVisible(true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initialComponents();
    }
    
    private void initialComponents(){
        mainPanel = new PaintPanel();
        converter = new Converter(-2, 1, -1, 1);
        setPainter = new FractalGradientPainter(converter, new MandelbrotSet(100), AlphaFunctions.SQRT);
        
        mainPanel.setPaintAction(graphics -> {
            setPainter.paint(graphics);
        });
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent event) {
                converter.setWidthPixels(mainPanel.getWidth());
                converter.setHeightPixels(mainPanel.getHeight());
            }
        });
        
        this.add(mainPanel);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
