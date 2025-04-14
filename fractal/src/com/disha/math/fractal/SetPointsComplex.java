package com.disha.math.fractal;

import com.disha.math.complex.ComplexNumber;

public interface SetPointsComplex {
    boolean isInSet(ComplexNumber point);
    int getCountOfPassIteration(ComplexNumber num);
    int getMaxIterations();
    void setMaxIterations(int maxIterations);
}
