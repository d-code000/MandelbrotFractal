package com.disha.app.fractal.logic;

import com.disha.app.fractal.painting.fractal.FractalPainter;
import com.disha.app.fractal.ui.PaintPanel;
import com.disha.converter.Border;
import com.disha.converter.Converter;

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

    // For rectangle selection with the right mouse button
    private Point selectionStart;
    private Point selectionEnd;
    private boolean isSelecting = false;

    private final HistoryManager historyManager;

    public PaintController(Component eventSource, PaintPanel mainPanel, Converter converter, FractalPainter fractalPainter, HistoryManager historyManager) {
        this.eventSource = eventSource;
        this.mainPanel = mainPanel;
        this.converter = converter;

        this.fractalPainter = fractalPainter;
        this.initialBorder = converter.border.clone();
        this.historyManager = historyManager;

        historyManager.addState(new State(initialBorder));

        // Set up the paint action to draw both the fractal and the selection rectangle
        this.mainPanel.setPaintAction(g -> {
            fractalPainter.paint(g);
            drawSelectionRectangle(g);
        });

        initMouseListeners();
        initKeyboardListeners();
        initResizeListeners();
    }

    private void initMouseListeners(){
        // Mouse press events for both left and right buttons
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                if (SwingUtilities.isLeftMouseButton(e)){
                    // Left button for panning
                    lastPressedPoint = e.getPoint();
                    lastPressedBorder = converter.border.clone();
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    // Right button for rectangle selection
                    selectionStart = e.getPoint();
                    selectionEnd = e.getPoint(); // Initialize to the same point
                    isSelecting = true;
                    mainPanel.repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && isSelecting) {
                    // When the right button is released, zoom to the selected area
                    if (selectionStart != null && selectionEnd != null) {
                        int width = Math.abs(selectionEnd.x - selectionStart.x);
                        int height = Math.abs(selectionEnd.y - selectionStart.y);

                        // Only zoom if the selection has some size
                        if (width > 5 && height > 5) {
                            zoomToSelection();
                            historyManager.saveState();
                        }
                    }

                    // Reset selection
                    isSelecting = false;
                    selectionStart = null;
                    selectionEnd = null;
                    mainPanel.repaint();
                }
            }
        });

        // Mouse drag events for both left and right buttons
        mainPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                if (SwingUtilities.isLeftMouseButton(e)){
                    // Left button drag for panning
                    var dx = converter.xScr2CrtRatio(e.getX() - lastPressedPoint.x);
                    var dy = converter.yScr2CrtRatio(e.getY() - lastPressedPoint.y);

                    converter.border = lastPressedBorder.clone();
                    converter.border.xShift(-dx);
                    converter.border.yShift(dy);

                    historyManager.saveState();
                    mainPanel.repaint();
                } else if (SwingUtilities.isRightMouseButton(e) && isSelecting) {
                    // Right button drag for rectangle selection
                    selectionEnd = e.getPoint();
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

            historyManager.saveState();
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
                    historyManager.undo();
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

    /**
     * Draws the selection rectangle if isSelecting is true
     */
    private void drawSelectionRectangle(Graphics g) {
        if (isSelecting && selectionStart != null && selectionEnd != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 
                    10.0f, new float[]{5.0f}, 0.0f)); // Dashed line

            int x = Math.min(selectionStart.x, selectionEnd.x);
            int y = Math.min(selectionStart.y, selectionEnd.y);
            int width = Math.abs(selectionEnd.x - selectionStart.x);
            int height = Math.abs(selectionEnd.y - selectionStart.y);

            g2d.drawRect(x, y, width, height);
            g2d.dispose();
        }
    }

    /**
     * Zooms to the selected rectangle area while maintaining the fractal's proportions
     */
    private void zoomToSelection() {
        if (selectionStart == null || selectionEnd == null) return;

        // Get the selection rectangle in screen coordinates
        int x1 = Math.min(selectionStart.x, selectionEnd.x);
        int y1 = Math.min(selectionStart.y, selectionEnd.y);
        int x2 = Math.max(selectionStart.x, selectionEnd.x);
        int y2 = Math.max(selectionStart.y, selectionEnd.y);

        // Calculate the width and height of the selection
        int width = x2 - x1;
        int height = y2 - y1;

        // Calculate the center of the selection
        int centerX = x1 + width / 2;
        int centerY = y1 + height / 2;

        // Get the current aspect ratio of the view
        double currentMinX = converter.border.getMinX();
        double currentMaxX = converter.border.getMaxX();
        double currentMinY = converter.border.getMinY();
        double currentMaxY = converter.border.getMaxY();
        double currentWidth = currentMaxX - currentMinX;
        double currentHeight = currentMaxY - currentMinY;
        double currentAspectRatio = currentWidth / currentHeight;

        // Adjust the selection rectangle to maintain the aspect ratio
        if (width / (double)height > currentAspectRatio) {
            // Selection is wider than the current view
            int newHeight = (int)(width / currentAspectRatio);
            int heightDiff = newHeight - height;
            y1 -= heightDiff / 2;
            y2 += heightDiff / 2;
        } else {
            // Selection is taller than the current view
            int newWidth = (int)(height * currentAspectRatio);
            int widthDiff = newWidth - width;
            x1 -= widthDiff / 2;
            x2 += widthDiff / 2;
        }

        // Convert screen coordinates to cartesian coordinates
        double minX = converter.xScr2Crt(x1);
        double maxX = converter.xScr2Crt(x2);
        double minY = converter.yScr2Crt(y2); // Note: y is inverted in screen coordinates
        double maxY = converter.yScr2Crt(y1);

        // Update the border with the new coordinates
        converter.border.setMinX(minX);
        converter.border.setMaxX(maxX);
        converter.border.setMinY(minY);
        converter.border.setMaxY(maxY);

        // Repaint the panel with the new view
        mainPanel.repaint();
    }
}
