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
    public void paint(Graphics graphics) {
        var g2d = (Graphics2D) graphics;

        g2d.setColor(color);

        for (int xPixel = 0; xPixel < converter.getWidthPixels(); xPixel++) {
            for (int yPixel = 0; yPixel < converter.getHeightPixels(); yPixel++) {
                var point = new ComplexNumber(converter.xScr2Crt(xPixel), converter.yScr2Crt(yPixel));
                var count = set.getCountOfPassIteration(point);
                if (count > 0) {
                    var ratio = (float) count / set.getMaxIterations();
                    var alpha = alphaFunction.apply(ratio);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                    g2d.fillRect(xPixel, yPixel, 1, 1);
                }
            }
        }
    }
}
