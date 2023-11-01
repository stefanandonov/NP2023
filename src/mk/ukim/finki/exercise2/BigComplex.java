package mk.ukim.finki.exercise2;

import java.math.BigDecimal;

public class BigComplex {

    private BigDecimal realPart;
    private BigDecimal imaginaryPart;

    public BigComplex(BigDecimal realPart, BigDecimal imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public BigComplex add(BigComplex number) {
        return new BigComplex(this.realPart.add(number.realPart),
                this.imaginaryPart.add(number.imaginaryPart));
    }

    @Override
    public String toString() {
        return String.format("%si + %sj", realPart.toString(), imaginaryPart.toString());
    }

    public static void main(String[] args) {
        BigComplex first = new BigComplex(new BigDecimal(2), new BigDecimal(5));
        BigComplex second = new BigComplex(new BigDecimal(4), new BigDecimal(3));

        System.out.println(first.add(second));
    }
}
