package com.disha.math.fractal;

import com.disha.math.complex.ComplexNumber;

public abstract class AbstractComplexSet implements SetPointsComplex {
    protected int maxIterations = 200;

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
    
    // This method is identical in both subclasses
    public boolean isInSet(ComplexNumber point) {
        return getCountOfPassIteration(point) == maxIterations;
    }
    
    // This method has different implementations in subclasses
    public abstract int getCountOfPassIteration(ComplexNumber num);
}