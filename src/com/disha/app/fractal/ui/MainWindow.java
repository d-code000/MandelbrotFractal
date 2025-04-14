package com.disha.app.fractal.ui;

import com.disha.app.fractal.painting.fractal.FractalPainter;
import com.disha.app.fractal.painting.fractal.gradient.AlphaFunctions;
import com.disha.app.fractal.painting.fractal.gradient.FractalGradientPainter;
import com.disha.converter.Border;
import com.disha.converter.Converter;
import com.disha.math.fractal.MandelbrotSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {
    private PaintPanel mainPanel;
    private Converter converter;
    private FractalPainter setPainter;
    
    private Point lastPressedPoint;
    private Border lastPressedBorder;
    
    
    public MainWindow() {
        setVisible(true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initialComponents();
    }
    
    private void initialComponents(){
        mainPanel = new PaintPanel();
        converter = new Converter(-2, 1, -1, 1);
        setPainter = new FractalGradientPainter(converter, new MandelbrotSet(30), AlphaFunctions.SQRT);
        
        mainPanel.setPaintAction(graphics -> {
            setPainter.paint(graphics);
        });
        
        // перерисовка окна при изменении размера окна
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent event) {
                converter.setWidthPixels(mainPanel.getWidth());
                converter.setHeightPixels(mainPanel.getHeight());
                mainPanel.repaint();
            }
        });
        
        // сохранение последней нажатой точки ЛКМ для перемещения
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                if (SwingUtilities.isLeftMouseButton(e)){
                    lastPressedPoint = e.getPoint();
                    lastPressedBorder = converter.border.clone();
                }
            }
        });
        
        // перемещение изображения при зажатой ЛКМ
        mainPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                if (SwingUtilities.isLeftMouseButton(e)){
                    var dx = converter.xScr2CrtRatio(e.getX() - lastPressedPoint.x);
                    var dy = converter.yScr2CrtRatio(e.getY() - lastPressedPoint.y);
                    
                    converter.border = lastPressedBorder.clone();
                    converter.border.xShift(-dx);
                    converter.border.yShift(dy);
                    
                    mainPanel.repaint();
                }
            }
        });
        
        // масштабирование по колесику мыши
        mainPanel.addMouseWheelListener(e -> {
            double zoomFactor = 1.1;
            int notches = e.getWheelRotation();
            double mouseX = converter.xScr2Crt(e.getX());
            double mouseY = converter.yScr2Crt(e.getY());

            double scale = (notches < 0) ? (1 / zoomFactor) : zoomFactor;

            double minX = converter.border.getMinX();
            double maxX = converter.border.getMaxX();
            double minY = converter.border.getMinY();
            double maxY = converter.border.getMaxY();

            double newWidth = (maxX - minX) * scale;
            double newHeight = (maxY - minY) * scale;
            
            double newMinX = mouseX - (mouseX - minX) * scale;
            double newMaxX = newMinX + newWidth;

            double newMinY = mouseY - (mouseY - minY) * scale;
            double newMaxY = newMinY + newHeight;

            converter.border.setMinX(newMinX);
            converter.border.setMaxX(newMaxX);
            converter.border.setMinY(newMinY);
            converter.border.setMaxY(newMaxY);

            mainPanel.repaint();
        });

        this.add(mainPanel);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
