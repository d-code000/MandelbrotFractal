package com.disha.app.fractal.painting;

import com.disha.converter.Converter;
import com.disha.math.complex.ComplexNumber;
import com.disha.math.fractal.SetPointsComplex;

import java.awt.*;

public class FractalMonoPainter extends FractalPainter implements Painter {
    
    public FractalMonoPainter(Converter converter, SetPointsComplex set) {
        super(converter, set);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(color);

        for (int xPixel = 0; xPixel < converter.getWidthPixels(); xPixel++) {
            for (int yPixel = 0; yPixel < converter.getHeightPixels(); yPixel++) {
                var point = new ComplexNumber(converter.xScr2Crt(xPixel), converter.yScr2Crt(yPixel));
                if (set.isInSet(point)) {
                    graphics.drawOval(xPixel, yPixel, 1, 1);
                }
            }
        }
    }
}
