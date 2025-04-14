package com.disha.app.fractal.painting;

import com.disha.converter.Converter;
import com.disha.math.fractal.SetPointsComplex;

import java.awt.*;

public abstract class FractalPainter extends AbstractPainter implements Painter {
    protected SetPointsComplex set;
    public Color color = Color.BLACK;
    
    public FractalPainter(Converter converter, SetPointsComplex set) {
        super(converter);
        setSet(set);
    }
    
    public void setSet(SetPointsComplex set) {
        this.set = set;
    }
}
