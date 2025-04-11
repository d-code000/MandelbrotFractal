package main.java.fractal;

import main.java.complex.ComplexNumber;

public class MandelbrotSet {
    private int maxIterations = 100;
    private final double R2 = 4.0;
    
    public boolean isInSet(ComplexNumber num){
        int i = 0;
        ComplexNumber z = new ComplexNumber();
        while (i++ < maxIterations && z.abs2() < R2) {
            z.times(z);
            z.plus(z);
        }
        return i > maxIterations;
    }
}
