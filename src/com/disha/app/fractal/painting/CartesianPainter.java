package com.disha.app.fractal.painting;

import main.java.converter.Converter;

import java.awt.*;

public class CartesianPainter extends AbstractPainter implements Painter {

    public CartesianPainter(int width, int height, Converter converter) {
        super(width, height, converter);
    }
    
    public void paint(Graphics graphics) {
        graphics.drawLine(converter.xCrt2Scr(converter.border.getMinX()), converter.yCrt2Scr(0.0), converter.xCrt2Scr(converter.border.getMaxX()), converter.yCrt2Scr(0.0));
        graphics.drawLine(converter.xCrt2Scr(0.0), converter.yCrt2Scr(converter.border.getMinY()), converter.xCrt2Scr(0.0), converter.yCrt2Scr(converter.border.getMaxY()));

        double epsilon = 1e-6;
        
        for (double i = converter.border.getMinX(); i <= converter.border.getMaxX(); i += 0.1) {
            double size;
            if (Math.abs(i) < epsilon) {
                continue;
            }
            else if (Math.abs(i - Math.round(i)) < epsilon) {
                graphics.setColor(Color.red);
                size = converter.yScr2CrtRatio(8);
                graphics.drawString(String.valueOf(Math.round(i)), converter.xCrt2Scr(i), converter.yCrt2Scr(- size - converter.yScr2CrtRatio(12)));
            }
            else if (Math.abs(Math.abs(i) % 1 - 0.5) < epsilon) {
                graphics.setColor(Color.blue);
                size = converter.yScr2CrtRatio(5);
            }
            else {
                graphics.setColor(Color.black);
                size = converter.yScr2CrtRatio(3);
            }
            graphics.drawLine(converter.xCrt2Scr(i), converter.yCrt2Scr(-size), converter.xCrt2Scr(i), converter.yCrt2Scr(size));
        }

        for (double i = converter.border.getMinY(); i <= converter.border.getMaxY(); i += 0.1) {
            double size;

            if (Math.abs(i) < epsilon) {
                continue;
            }
            else if (Math.abs(i - Math.round(i)) < epsilon) {
                graphics.setColor(Color.red);
                size = converter.xScr2CrtRatio(8);
                graphics.drawString(String.valueOf(Math.round(i)), converter.xCrt2Scr(size + converter.xScr2CrtRatio(6)), converter.yCrt2Scr(i));
            }
            else if (Math.abs(Math.abs(i) % 1 - 0.5) < epsilon) {
                graphics.setColor(Color.blue);
                size = converter.xScr2CrtRatio(5);
            }
            else {
                graphics.setColor(Color.black);
                size = converter.xScr2CrtRatio(3);
            }
            graphics.drawLine(converter.xCrt2Scr(-size), converter.yCrt2Scr(i), converter.xCrt2Scr(size), converter.yCrt2Scr(i));
        }
    }
}