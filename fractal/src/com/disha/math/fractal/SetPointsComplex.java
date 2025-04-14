package com.disha.math.fractal;

import com.disha.math.complex.ComplexNumber;

public interface SetPointsComplex {
    public boolean isInSet(ComplexNumber point);
    public int getCountOfPassIteration(ComplexNumber num);
}
