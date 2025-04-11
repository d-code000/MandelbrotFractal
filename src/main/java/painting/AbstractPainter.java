package main.java.painting;

import main.java.converter.Converter;

import java.awt.*;

public abstract class AbstractPainter implements Painter {
    protected Dimension size;
    protected Converter converter;

    public AbstractPainter(Dimension size, Converter converter) {
        setSize(size);
        this.converter = converter;
    }

    public AbstractPainter(int width, int height, Converter converter) {
        this(new Dimension(width, height), converter);
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
