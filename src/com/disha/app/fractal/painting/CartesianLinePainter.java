package com.disha.app.fractal.painting;

import com.disha.converter.Converter;

import java.awt.*;

public class CartesianLinePainter extends AbstractPainter implements Painter {
    
    public CartesianLinePainter(Converter converter) {
        super(converter);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.drawLine(converter.xCrt2Scr(converter.border.getMinX()), converter.yCrt2Scr(0), converter.xCrt2Scr(converter.border.getMaxX()), converter.yCrt2Scr(0));
        graphics.drawLine(converter.xCrt2Scr(0), converter.yCrt2Scr(converter.border.getMinY()), converter.xCrt2Scr(0), converter.yCrt2Scr(converter.border.getMaxY()));
    }
}
