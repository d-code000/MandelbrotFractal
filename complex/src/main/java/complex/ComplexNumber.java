package main.java.complex;

public class ComplexNumber {
    private double real, imag;
    
    public ComplexNumber() {
        this.real = 0;
        this.imag = 0;
    }
    
    public ComplexNumber(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }
    
    public double getReal() {
        return real;
    }
    
    public double getImag() {
        return imag;
    }
    
    public void plus(ComplexNumber num) {
        this.real += num.real;
        this.imag += num.imag;
    }
    
    public void times(ComplexNumber num) {
        var newReal = this.real * num.real - this.imag * num.imag;
        var newImag = this.real * num.imag + this.imag * num.real;
        
        this.real = newReal;
        this.imag = newImag;
    }
    
    public double abs2(){
        return (real * real) + (imag * imag);
    }

    public double abs(){
        return Math.sqrt(abs2());
    }
}
