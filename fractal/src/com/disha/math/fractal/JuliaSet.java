package com.disha.math.fractal;

import com.disha.math.complex.ComplexNumber;

public class JuliaSet implements SetPointsComplex {
    private int maxIterations = 200;
    private ComplexNumber c; // The fixed complex parameter for the Julia set

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
    
    // Default constructor with c = (-0.7, 0.27) as a common interesting value
    public JuliaSet() {
        this.c = new ComplexNumber(-0.7, 0.27);
    }
    
    // Constructor with specified parameter c
    public JuliaSet(ComplexNumber c) {
        this.c = new ComplexNumber(c.getReal(), c.getImag());
    }
    
    // Constructor with specified parameter c and maxIterations
    public JuliaSet(ComplexNumber c, int maxIterations) {
        this.c = new ComplexNumber(c.getReal(), c.getImag());
        setMaxIterations(maxIterations);
    }
    
    // Get the fixed complex parameter
    public ComplexNumber getC() {
        return new ComplexNumber(c.getReal(), c.getImag());
    }
    
    // Set the fixed complex parameter
    public void setC(ComplexNumber c) {
        this.c = new ComplexNumber(c.getReal(), c.getImag());
    }
    
    public int getCountOfPassIteration(ComplexNumber z) {
        int i = 0;
        // For Julia set, z starts as the point we're testing
        ComplexNumber zn = new ComplexNumber(z.getReal(), z.getImag());
        
        while (i++ < maxIterations && zn.abs2() < 4.0) {
            zn.times(zn);    // z = z²
            zn.plus(c);      // z = z² + c (using the fixed parameter c)
        }
        return i - 1;
    }
    
    public boolean isInSet(ComplexNumber point) {
        return getCountOfPassIteration(point) == maxIterations;
    }
}