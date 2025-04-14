package com.disha.app.fractal.painting.fractal.gradient;

@FunctionalInterface
public interface AlphaFunction {
    float apply(float ratio);
}
