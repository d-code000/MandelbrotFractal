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
        this.real = this.real * num.real - this.imag * num.imag;
        this.imag = this.real * num.imag + this.imag * num.real;
    }
    
    public double abs2(){
        return Math.pow(real, 2) + Math.pow(imag, 2);
    }

    public double abs(){
        return Math.sqrt(abs2());
    }
}
