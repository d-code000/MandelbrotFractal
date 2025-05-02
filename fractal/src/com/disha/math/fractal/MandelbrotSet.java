package com.disha.math.fractal;

import com.disha.math.complex.ComplexNumber;

public class MandelbrotSet extends AbstractComplexSet {

    public MandelbrotSet(){}

    public MandelbrotSet(int maxIterations){
        setMaxIterations(maxIterations);
    }

    public int getCountOfPassIteration(ComplexNumber num){
        int i = 0;
        ComplexNumber z = new ComplexNumber();
        while (i++ < maxIterations && z.abs2() < 4.0) {
            z.times(z);
            z.plus(num);
        }
        return i - 1;
    }

}
