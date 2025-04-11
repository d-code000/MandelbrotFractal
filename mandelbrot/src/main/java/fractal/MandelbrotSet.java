package main.java.fractal;

import main.java.complex.ComplexNumber;

public class MandelbrotSet {
    private int maxIterations = 200;
    public final double R2 = 4.0;
    
    public int getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        if (maxIterations < 0) {
            throw new IllegalArgumentException("maxIterations cannot be negative");
        }
        if (maxIterations < 10) {
            throw new IllegalArgumentException("maxIterations too small (must be at least 10)");
        }
        this.maxIterations = maxIterations;
    }
    
    public boolean isInSet(ComplexNumber num){
        int i = 0;
        ComplexNumber z = new ComplexNumber();
        while (i++ < maxIterations && z.abs2() < R2) {
            z.times(z);
            z.plus(num);
        }
        return i > maxIterations;
    }
}
