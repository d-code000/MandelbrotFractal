package com.disha.math.fractal;

import com.disha.math.complex.ComplexNumber;
import com.disha.math.fractal.MandelbrotSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class MandelbrotSetTest {
    
    @Test
    public void isInSet(){
        var f = new MandelbrotSet();

        var inSet = new ArrayList<>(Arrays.asList(
                new ComplexNumber(0, 0),
                new ComplexNumber(-1, 0),
                new ComplexNumber(-0.75, 0),
                new ComplexNumber(0.25, 0.5),
                new ComplexNumber(-0.1015, 0.633)
        ));

        var notInSet = new ArrayList<>(Arrays.asList(
                new ComplexNumber(2, 0),
                new ComplexNumber(-2, 0),
                new ComplexNumber(1, 1),
                new ComplexNumber(-1.5, 1.5),
                new ComplexNumber(0.5, 0.75)
        ));
        
        for (ComplexNumber c : inSet) {
            Assertions.assertTrue(f.isInSet(c));
        }
        
        for (ComplexNumber c : notInSet) {
            Assertions.assertFalse(f.isInSet(c));
        }
    }
    
    @Test
    public void maxIterations(){
        var f = new MandelbrotSet();
        f.setMaxIterations(1000);
        
        Assertions.assertEquals(1000, f.getMaxIterations());
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {f.setMaxIterations(0);});
        Assertions.assertThrows(IllegalArgumentException.class, () -> {f.setMaxIterations(-1);});
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {f.setMaxIterations(5);});
    }
}
