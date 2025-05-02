package com.disha.app.fractal.painting;

import com.disha.converter.Converter;

import java.awt.*;

public abstract class AbstractPainter implements Painter {
    protected Dimension size;
    protected final Converter converter;
    
    public AbstractPainter(Converter converter) {
        this.converter = converter;
    }

    @Override
    public Dimension getSize() {
        return new Dimension(size);
    }

    @Override
    public void setSize(Dimension size) {
        this.size = size;
    }

    @Override
    public void setSize(int width, int height) {
        this.size = new Dimension(width, height);
    }
}
