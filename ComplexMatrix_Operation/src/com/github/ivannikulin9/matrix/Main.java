package com.github.ivannikulin9.matrix;

import java.util.Scanner; // java.util — пакет стандартной библиотеки, Scanner — класс для чтения ввода

public class Main {
    private static final Scanner scanner = new Scanner(System.in); // создаём объект Scanner, который читает из стандартного ввода(с клавиатуры), .in - поле класса
    private static ComplexMatrix matrixA;
    private static ComplexMatrix matrixB;

    public static void main(String[] args) {
        System.out.println("=== Работа с матрицами комплексных чисел ===");

        matrixA = readMatrix("A");
        matrixB = readMatrix("B");

        System.out.println("\nМатрица A:");
        System.out.println(matrixA); // неявно вызывает matrix.toString(), который переопределен
        System.out.println("Матрица B:");
        System.out.println(matrixB);

        boolean running = true; // бесконечный цикл с меню
        while (running) {
            printMenu();
            int choice = readInt("Ваш выбор: ");

            try { // если возникнет исключение — оно будет перехвачено в catch
                switch (choice) {
                    case 1 -> System.out.println("A + B:\n" + matrixA.add(matrixB)); // конкатенация(при ней Java вызывает toString() у результата)
                    case 2 -> System.out.println("A * B:\n" + matrixA.multiply(matrixB));
                    case 3 -> System.out.println("A / B (поэлементно):\n" + matrixA.divide(matrixB));
                    case 4 -> System.out.println("A транспонированная:\n" + matrixA.transpose());
                    case 5 -> System.out.println("Определитель A: " + matrixA.determinant());
                    case 6 -> System.out.println("Определитель B: " + matrixB.determinant());
                    case 7 -> System.out.println("B + A:\n" + matrixB.add(matrixA));
                    case 8 -> System.out.println("B * A:\n" + matrixB.multiply(matrixA));
                    case 9 -> System.out.println("B транспонированная:\n" + matrixB.transpose());
                    case 0 -> {
                        System.out.println("Завершение работы.");
                        running = false;
                    }
                    default -> System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== МЕНЮ ОПЕРАЦИЙ =====");
        System.out.println("1 - A + B");
        System.out.println("2 - A * B");
        System.out.println("3 - A / B (поэлементно)");
        System.out.println("4 - Транспонировать A");
        System.out.println("5 - Определитель A");
        System.out.println("6 - Определитель B");
        System.out.println("7 - B + A");
        System.out.println("8 - B * A");
        System.out.println("9 - Транспонировать B");
        System.out.println("0 - Выход");
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Ошибка! Введите целое число: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static ComplexMatrix readMatrix(String name) {
        System.out.println("\nВведите размеры матрицы " + name + ":");
        int rows = readInt("Строк: ");
        int cols = readInt("Столбцов: ");

        ComplexMatrix matrix = new ComplexMatrix(rows, cols);

        System.out.println("Введите " + (rows * cols) + " комплексных чисел.");
        System.out.println("Каждое число — два значения через пробел: <действительная> <мнимая>");
        System.out.println("Например: 3 -2  означает  3 - 2i");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("Элемент [%d][%d]: ", i, j);
                double real = scanner.nextDouble();
                double imag = scanner.nextDouble();
                matrix.set(i, j, new ComplexNumber(real, imag));
            }
        }
        return matrix;
    }
}