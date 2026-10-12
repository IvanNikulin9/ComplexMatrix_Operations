package com.github.ivannikulin9.matrix;

public class ComplexNumber { // в файле может быть несколько классов верхнего уровня, но только один может быть public, и его имя должно совпадать с именем файла.
    private final double real; // private - видно только внутри этого класса
    private final double imaginary; // final — значение нельзя изменить после создания объекта

    public ComplexNumber(double real, double imaginary) { // конструктор
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() { return real; } // геттер
    public double getImaginary() { return imaginary; } // геттер

    public ComplexNumber add(ComplexNumber other) { // сложение
        return new ComplexNumber(this.real + other.real,
                this.imaginary + other.imaginary);
    }

    public ComplexNumber subtract(ComplexNumber other) { // вычитание
        return new ComplexNumber(this.real - other.real,
                this.imaginary - other.imaginary);
    }

    public ComplexNumber multiply(ComplexNumber other) { // умножение: (a+bi)(c+di) = (ac - bd) + (ad + bc)i
        double newReal = this.real * other.real - this.imaginary * other.imaginary;
        double newImag = this.real * other.imaginary + this.imaginary * other.real;
        return new ComplexNumber(newReal, newImag);
    }

    public ComplexNumber divide(ComplexNumber other) { // деление: (a+bi)/(c+di) = ((ac+bd) + (bc-ad)i) / (c²+d²)
        double denominator = other.real * other.real + other.imaginary * other.imaginary;
        if (denominator == 0) {
            throw new ArithmeticException("Деление на ноль (комплексный ноль)");
        }
        double newReal = (this.real * other.real + this.imaginary * other.imaginary) / denominator;
        double newImag = (this.imaginary * other.real - this.real * other.imaginary) / denominator;
        return new ComplexNumber(newReal, newImag);
    }

    public double abs() { // модуль (нужен для сравнения с нулём при вычислении определителя)
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    @Override
    public String toString() { // переопределение метода родительского класса(показываем через @Override)
        if (imaginary >= 0) {
            return String.format("%.2f+%.2fi", real, imaginary); //String.format(...) — статический метод форматирования строк.
        } else {
            return String.format("%.2f%.2fi", real, imaginary);
        }
    }
}