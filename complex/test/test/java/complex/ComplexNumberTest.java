package test.java.complex;

import main.java.complex.ComplexNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComplexNumberTest {
    
    @Test
    public void plus(){
        ComplexNumber num = new ComplexNumber(1.0, 2.0);
        num.plus(new ComplexNumber(1.0, -3.0));

        Assertions.assertEquals(2.0, num.getReal());
        Assertions.assertEquals(-1.0, num.getImag());
    }
    
    @Test
    public void times(){
        ComplexNumber num = new ComplexNumber(-3.0, 2.0);
        num.times(new ComplexNumber(2.0, -5.0));
        
        Assertions.assertEquals(4.0, num.getReal());
        Assertions.assertEquals(19, num.getImag());
    }
    
    @Test
    public void abs(){
        ComplexNumber num = new ComplexNumber(-4.0, 3.0);
        
        Assertions.assertEquals(5.0, num.abs());
    }
}
