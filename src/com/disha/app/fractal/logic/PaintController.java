package com.disha.app.fractal.logic;

import com.disha.app.fractal.painting.fractal.FractalPainter;
import com.disha.app.fractal.painting.fractal.gradient.AlphaFunctions;
import com.disha.app.fractal.painting.fractal.gradient.FractalGradientPainter;
import com.disha.app.fractal.ui.PaintPanel;
import com.disha.converter.Border;
import com.disha.converter.Converter;
import com.disha.math.fractal.MandelbrotSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PaintController {
    
    private final PaintPanel mainPanel;
    private final Converter converter;
    private final FractalPainter fractalPainter;
    private final Component eventSource;
    
    private final Border initialBorder;
    private Point lastPressedPoint;
    private Border lastPressedBorder;
    
    private final HistoryManager historyManager;

    public PaintController(Component eventSource, PaintPanel mainPanel, Converter converter) {
        this.eventSource = eventSource;
        this.mainPanel = mainPanel;
        this.converter = converter;
        
        this.fractalPainter = new FractalGradientPainter(converter, new MandelbrotSet(100), AlphaFunctions.SQRT);
        this.initialBorder = converter.border.clone();
        this.historyManager = new HistoryManager();

        this.mainPanel.setPaintAction(graphics -> {
            fractalPainter.paint(graphics);
        });
        
        initMouseListeners();
        initKeyboardListeners();
        initResizeListeners();
    }

    private void initMouseListeners(){
        // Сохранение последней нажатой точки ЛКМ для перемещения
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                if (SwingUtilities.isLeftMouseButton(e)){
                    lastPressedPoint = e.getPoint();
                    lastPressedBorder = converter.border.clone();
                }
            }
        });

        // Перемещение изображения при зажатой ЛКМ
        mainPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                if (SwingUtilities.isLeftMouseButton(e)){
                    var dx = converter.xScr2CrtRatio(e.getX() - lastPressedPoint.x);
                    var dy = converter.yScr2CrtRatio(e.getY() - lastPressedPoint.y);

                    converter.border = lastPressedBorder.clone();
                    converter.border.xShift(-dx);
                    converter.border.yShift(dy);
                    
                    historyManager.addStep(new Step(converter.border.clone()));
                    mainPanel.repaint();
                }
            }
        });

        // Масштабирование по колесику мыши
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

            historyManager.addStep(new Step(converter.border.clone()));
            mainPanel.repaint();
        });
    }
    
    private void initKeyboardListeners(){
        eventSource.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Home
                if (e.getKeyCode() == KeyEvent.VK_HOME) {
                    converter.border = initialBorder.clone();
                    mainPanel.repaint();
                }
                // Ctrl + Z
                if (e.getKeyCode() == KeyEvent.VK_Z && (e.isControlDown())) {
                    var lastStep = historyManager.undo();
                    converter.border = lastStep.border;
                    mainPanel.repaint();
                }
            }
        });
    }
    
    private void initResizeListeners(){
        eventSource.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent event) {
                fractalPainter.setSize(mainPanel.getWidth(), mainPanel.getHeight());

                var sizeMin = Math.min(mainPanel.getWidth(), mainPanel.getHeight());
                converter.setHeightPixels(sizeMin);
                converter.setWidthPixels(sizeMin);
                mainPanel.repaint();
            }
        });
    }
}
