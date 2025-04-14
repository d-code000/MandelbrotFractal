package com.disha.app.fractal.painting.fractal.gradient;

public class AlphaFunctions {
    public static final AlphaFunction LINEAR = r -> r;
    public static final AlphaFunction SQRT = r -> (float) Math.sqrt(r);
    public static final AlphaFunction SIN = r -> (float) Math.sin(r * Math.PI / 2);
}
