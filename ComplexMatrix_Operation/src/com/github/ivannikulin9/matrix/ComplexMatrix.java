package com.github.ivannikulin9.matrix;

public class ComplexMatrix {
    private final ComplexNumber[][] data;
    private final int rows;
    private final int cols;

    public ComplexMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new ComplexNumber[rows][cols];
        // Заполняем нулями
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = new ComplexNumber(0, 0); // заполняем матрицу нулевыми комплексными числами
            }
        }
    }

    public int getRows() { return rows; } // геттер
    public int getCols() { return cols; } // геттер

    public ComplexNumber get(int i, int j) { return data[i][j]; } // геттер матрицы
    public void set(int i, int j, ComplexNumber value) { data[i][j] = value; }

    public ComplexMatrix add(ComplexMatrix other) { // сложение матриц
        if (this.rows != other.rows || this.cols != other.cols) { // проверяем условия сложения матриц
            throw new IllegalArgumentException("Размеры матриц должны совпадать");
        }
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.data[i][j].add(other.data[i][j])); // складываем элементы матриц и записываем результат в новую матрицу
            }
        }
        return result;
    }

    public ComplexMatrix multiply(ComplexMatrix other) { // умножение матриц
        if (this.cols != other.rows) { // проверка условий умножения матриц
            throw new IllegalArgumentException("Число столбцов первой матрицы должно равняться числу строк второй");
        }
        ComplexMatrix result = new ComplexMatrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                ComplexNumber sum = new ComplexNumber(0, 0); // накопитель суммы
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.add(this.data[i][k].multiply(other.data[k][j])); // умножаем два комплексных числа и добавляем в накопитель суммы
                }
                result.set(i, j, sum); // записываем получившееся значение в итоговую матрицу
            }
        }
        return result;
    }

    public ComplexMatrix divide(ComplexMatrix other) { // поэлементное деление матриц
        if (this.rows != other.rows || this.cols != other.cols) { // проверка условий деления матриц
            throw new IllegalArgumentException("Размеры матриц должны совпадать");
        }
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.data[i][j].divide(other.data[i][j]));
            }
        }
        return result;
    }

    public ComplexMatrix transpose() { // транспонирование
        ComplexMatrix result = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(j, i, this.data[i][j]); // просто меняем местами i и j
            }
        }
        return result;
    }

    public ComplexNumber determinant() { // определитель
        if (rows != cols) {
            throw new IllegalArgumentException("Определитель только для квадратных матриц");
        }
        return determinantRecursive(this.data, rows);
    }

    private ComplexNumber determinantRecursive(ComplexNumber[][] matrix, int n) {
        if (n == 1) {
            return matrix[0][0];
        }
        if (n == 2) {
            return matrix[0][0].multiply(matrix[1][1])
                    .subtract(matrix[0][1].multiply(matrix[1][0]));
        }
        ComplexNumber det = new ComplexNumber(0, 0);
        for (int col = 0; col < n; col++) {
            ComplexNumber[][] minor = new ComplexNumber[n - 1][n - 1]; // минор
            for (int i = 1; i < n; i++) { // нулевая строка вычеркнута
                int minorCol = 0; // индекс столбца в миноре
                for (int j = 0; j < n; j++) {
                    if (j == col) continue; // пропускаем столбец, который вычеркнут
                    minor[i - 1][minorCol++] = matrix[i][j]; // формируем минор(сдвиг на 1, потому что строка 0 вычеркнута)
                }
            }
            ComplexNumber term = matrix[0][col].multiply(determinantRecursive(minor, n - 1)); // рекурсивно вычисляем определитель минора
            if (col % 2 == 0) {
                det = det.add(term);
            } else {
                det = det.subtract(term);
            }
        }
        return det;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // класс для эффективной сборки строк
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j]).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}