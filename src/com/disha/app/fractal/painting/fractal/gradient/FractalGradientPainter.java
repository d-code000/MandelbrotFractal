package com.disha.app.fractal.painting.fractal.gradient;

import com.disha.app.fractal.painting.fractal.FractalPainter;
import com.disha.app.fractal.painting.Painter;
import com.disha.converter.Converter;
import com.disha.math.complex.ComplexNumber;
import com.disha.math.fractal.SetPointsComplex;

import java.awt.*;

public class FractalGradientPainter extends FractalPainter implements Painter {
    private final AlphaFunction alphaFunction;
    
    public FractalGradientPainter(Converter converter, SetPointsComplex set, AlphaFunction alphaFunction) {
        super(converter, set);
        this.alphaFunction = alphaFunction;
    }

    @Override
    protected int drawPoint(ComplexNumber point) {
        int count = set.getCountOfPassIteration(point);
        float ratio = (float) count / set.getMaxIterations();
        float alpha = alphaFunction.apply(ratio);
        
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        
        r = (int)(r * ratio);
        g = (int)(g * ratio);
        b = (int)(b * ratio);

        int a = (int)(alpha * 255);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
