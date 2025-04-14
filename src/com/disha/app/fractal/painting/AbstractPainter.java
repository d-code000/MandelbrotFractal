package com.disha.app.fractal.painting;

import com.disha.converter.Converter;

public abstract class AbstractPainter implements Painter {
    protected Converter converter;
    
    public AbstractPainter(Converter converter) {
        this.converter = converter;
    }
}
