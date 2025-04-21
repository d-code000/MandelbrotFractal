package com.disha.app.fractal.painting.fractal.mono;

import com.disha.app.fractal.painting.fractal.FractalPainter;
import com.disha.app.fractal.painting.Painter;
import com.disha.converter.Converter;
import com.disha.math.complex.ComplexNumber;
import com.disha.math.fractal.SetPointsComplex;

import java.awt.*;

public class FractalMonoPainter extends FractalPainter implements Painter {
    
    public FractalMonoPainter(Converter converter, SetPointsComplex set) {
        super(converter, set);
    }

    @Override
    protected int drawPixel(ComplexNumber point) {
        if (set.isInSet(point)) {
            return color.getRGB();
        }
        
        return Color.WHITE.getRGB();
    }
}
