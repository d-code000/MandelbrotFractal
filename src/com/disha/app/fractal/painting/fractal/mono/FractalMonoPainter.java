package com.disha.app.fractal.painting.fractal.mono;

import com.disha.app.fractal.painting.fractal.FractalPainter;
import com.disha.app.fractal.painting.Painter;
import com.disha.converter.Converter;
import com.disha.math.fractal.SetPointsComplex;

import java.awt.*;

public class FractalMonoPainter extends FractalPainter implements Painter {
    
    public FractalMonoPainter(Converter converter, SetPointsComplex set) {
        super(converter, set);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(color);
        
        var calcData = calcDisplayResult(set::isInSet, Boolean.class);

        for (int xPixel = 0; xPixel < converter.getWidthPixels(); xPixel++) {
            for (int yPixel = 0; yPixel < converter.getHeightPixels(); yPixel++) {
                if (calcData[xPixel][yPixel]) {
                    graphics.drawOval(xPixel, yPixel, 1, 1);
                }
            }
        }
    }
}
