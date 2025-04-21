package com.disha.app.fractal.ui;

import com.disha.app.fractal.painting.fractal.FractalPainter;
import com.disha.app.fractal.painting.fractal.gradient.AlphaFunctions;
import com.disha.app.fractal.painting.fractal.gradient.FractalGradientPainter;
import com.disha.converter.Border;
import com.disha.converter.Converter;
import com.disha.math.fractal.MandelbrotSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    private PaintPanel mainPanel;
    private Converter converter;
    private FractalPainter setPainter;
    private Border initialBorder;
    
    private Point lastPressedPoint;
    private Border lastPressedBorder;
    
    
    public MainWindow() {
        setVisible(true);
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initialComponents();
    }
    
    private void initialComponents(){
        mainPanel = new PaintPanel();
        converter = new Converter(-2, 1, -1.5, 1.5);
        initialBorder = converter.border.clone();
        setPainter = new FractalGradientPainter(converter, new MandelbrotSet(100), AlphaFunctions.SQRT);
        
        mainPanel.setPaintAction(graphics -> {
            setPainter.paint(graphics);
        });
        
        // перерисовка окна при изменении размера окна с сохранением пропорций
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent event) {
                setPainter.setSize(mainPanel.getWidth(), mainPanel.getHeight());
                
                var sizeMin = Math.min(mainPanel.getWidth(), mainPanel.getHeight());
                converter.setHeightPixels(sizeMin);
                converter.setWidthPixels(sizeMin);
                mainPanel.repaint();
            }
        });
        
        // возвращение к начальному виду при нажатии кнопки <Home>
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_HOME) {
                    converter.border = initialBorder.clone();
                    mainPanel.repaint();
                }
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
