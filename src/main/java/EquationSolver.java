import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;

public class EquationSolver {
    /*------Цвета для консоли-------*/
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";

    Scanner scn = new Scanner(System.in);
    FileOutputStream fos;
    PrintStream filePrintStream;
    GraphController gc = new GraphController();

    private void chordMethod(double a, double b, Function<Double, Double> func, Function<Double, Double> dFunc, double E){
        //Метод Хорд
        // a, b - интервал, func - функция, E - необходимая погрешность

        int count = 0;
        double x, x0, tmpX = 12345;

        if(func.apply(a)*func.apply(b) >= 0){
            System.out.println("Корней нет! Функция имеет разные знаки на концах интервала!");
        }else {

            System.out.println("Как вы хотите вывести решение?");
            System.out.println("1. В файл");
            System.out.println("2. В консоль");
            boolean fileOut = (scn.nextInt() == 1);
            if(fileOut){
                System.out.println("Поместите файл в папку src и введите название файла:");
                String path = "PATH TO src" + scn.next();
                try {
                    fos = new FileOutputStream(path);
                    filePrintStream = new PrintStream(fos);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(fileOut){
                    filePrintStream.printf("%15s %15s %15s %15s %15s %15s %15s %15s %n",
                            "Шаг", "a", "b", "x", "func(a)", "func(b)", "func(x)", "|b - a|");
            }else {
                System.out.printf("%15s %15s %15s %15s %15s %15s %15s %15s %n",
                        "Шаг", "a", "b", "x", "func(a)", "func(b)", "func(x)", "|b - a|");
            }

            x = (a * func.apply(b) - b * func.apply(a)) / (func.apply(b) - func.apply(a));

            do{
                count++;
                if(count != 1) tmpX = x;

                x = (a * func.apply(b) - b * func.apply(a)) / (func.apply(b) - func.apply(a));

                x0 = tmpX;

                if(func.apply(a) * func.apply(x) > 0){
                    a = x;
                } else{
                    b =x;
                }
                    
                if(fileOut){
                    filePrintStream.printf("%15d %15f %15f %15f %15f %15f %15f %15f %n",
                            count, a, b, x, func.apply(a), func.apply(b), func.apply(x), Math.abs(b - a));
                }else {
                    System.out.printf("%15d %15f %15f %15f %15f %15f %15f %15f %n",
                            count, a, b, x, func.apply(a), func.apply(b), func.apply(x), Math.abs(b - a));
                }

                if (count > 350){
                    break;
                }

            }while (Math.abs(func.apply(x)) > E && Math.abs(x-x0) > E);

            if(fileOut){
                    filePrintStream.close();
            }
        }
    }

    private void newtonMethod(double a, double b, Function<Double, Double> func, Function<Double, Double> dFunc, double E){
        //Метод Ньютона
        //a, b - начальные границы, func - функция, dFunc- производная функции, E - необходимая погрешность
        int count = 0;
        Function<Double, Double> ddfunc = x -> 6*x + 4.56;
        double x0, x, div;

        if(func.apply(a)*func.apply(b) >=0){
            System.out.println("Корней нет! Знаки функции на краях промежутка совпадают!");
        }else {

            System.out.println("Как вы хотите вывести решение?");
            System.out.println("1. В файл");
            System.out.println("2. В консоль");
            boolean fileOut = (scn.nextInt() == 1);
            if (fileOut) {
                System.out.println("Поместите файл в папку src и введите название файла:");
                String path = "PATH TO src" + scn.next();
                try {
                    fos = new FileOutputStream(path);
                    filePrintStream = new PrintStream(fos);
                } catch (IOException e) {
                    System.err.println("Ошибка чтения файла!");
                }
            }
            if (fileOut) {
                filePrintStream.printf("%15s %15s %15s %15s %15s %15s %n",
                        "Шаг", "Xn", "func(Xn)", "dfunc(Xn)", "Xn+1", "|Xn+1 - Xn|");
            } else {
                System.out.printf("%15s %15s %15s %15s %15s %15s %n",
                        "Шаг", "Xn", "func(Xn)", "dfunc(Xn)", "Xn+1", "|Xn+1 - Xn|");
            }

            //Если значения функции и второй производной функции имеют одинаковые знаки, то берём Xn за левую границу
            if(func.apply(a) * ddfunc.apply(a) > 0){
                x = a;
            }else {
                x = b;
            }
            div = func.apply(x) / dFunc.apply(x);

            do{
                count++;

                x0 = x;
                x = x0 - div;
                div = func.apply(x) / dFunc.apply(x);

                if (fileOut) {
                    filePrintStream.printf("%15d %15f %15f %15f %15f %15f %n",
                            count, x0, func.apply(x), dFunc.apply(x), x, Math.abs(x - x0));
                } else {
                    System.out.printf("%15d %15f %15f %15f %15f %15f %n",
                            count, x0, func.apply(x), dFunc.apply(x), x, Math.abs(x - x0));
                }
                if (count > 350){
                    break;
                }
            }while(Math.abs(x-x0) > E);

            if (fileOut) {
                filePrintStream.close();
            }
        }
    }

    private void simpleIterationsMethod(double a, double b, Function<Double, Double> func, Function<Double, Double> dFunc, double E){
        //a, b- границы, func - функция, dFunc - произовдная функции, E - необходимая погрешность

        Function<Double, Double> dfi = x -> (x*(5.802*x+8.81904)) / 3.740356;
        int count = 0;
        double x0, x, lambda;

        if(func.apply(a)*func.apply(b) > 0){
            System.out.println("Решений нет! На краях интервала функция имеет одинаковые знаки!");

        }else{

            System.out.println("Как вы хотите вывести решение?");
            System.out.println("1. В файл");
            System.out.println("2. В консоль");

            boolean fileOut = (scn.nextInt() == 1);

            if(fileOut){
                System.out.println("Поместите файл в папку src и введите название файла:");
                String path = "PATH TO src" + scn.next();
                try {
                    fos = new FileOutputStream(path);
                    filePrintStream = new PrintStream(fos);
                }catch (IOException e){
                    System.err.println("Ошибка чтения файла!");
                }
            }

            if(fileOut){
                filePrintStream.printf("%15s %15s %15s %15s %15s %n",
                        "Шаг", "Xn", "func(Xn)", "Xn+1", "|Xn+1 - Xn|");
            }else {
                System.out.printf("%15s %15s %15s %15s %15s %n",
                        "Шаг", "Xn", "func(Xn)", "Xn+1", "|Xn+1 - Xn|");
            }
   if (dFunc.apply(a) > dFunc.apply(b)) {
                x = a;
            } else {
                x = b;
            }

            lambda = -1 / dFunc.apply(x);
            double dfia = (3 * Math.pow(a, 2) + 4.56 * a - 1.934) * lambda + 1;
            double dfib = (3 * Math.pow(b, 2) + 4.56 * b - 1.934) * lambda + 1;

            //Проверка сходимости метода простой итерации

            if (Math.abs(dfia) >= 1 & Math.abs(dfib) >= 1) {
                System.err.println("Начальное приближение выбрано неверно, не выполняется достаточное условие сходимости метода.");

            } else {

                x = x + lambda * func.apply(x);

                do {
                    count++;
                    x0 = x;
                    x = x0 + lambda * func.apply(x0);

                    if (fileOut) {
                        filePrintStream.printf("%15d %15f %15f %15f %15f %n",
                                count, x0, func.apply(x0), x, Math.abs(x - x0));
                    } else {
                        System.out.printf("%15d %15f %15f %15f %15f %n",
                                count, x0, func.apply(x0), x, Math.abs(x - x0));
                    }

                    if (count > 350) {
                        break;
                    }

                } while (Math.abs(x - x0) > E | Math.abs(func.apply(x0)) > E);

                if (fileOut) {
                    filePrintStream.close();
                }
            }
        }
    }
    public void chordMethodSolver(Function<Double, Double> func, Function<Double, Double> dfunc){
        System.out.println("Каким образом будете вводить данные?\n");
        System.out.println("1. Из файла");
        System.out.println("2. Вручную");

        switch (scn.nextInt()) {
            case 1:
                System.out.println(ANSI_CYAN + "Некоторые особенности чтения файлов:" + ANSI_RESET);
                System.out.println("Файл должен содержать левую границу(a), правую границу(b), желаемую погрешность(E).");
                System.out.println("Все числа должны быть записаны через пробел.\n");
                System.out.println("Поместите файл в папку src и введите название файла:");
                String path = "PATH TO src" + scn.next();
                try {
                    Scanner sc = new Scanner(new File(path));

                    double a = sc.nextDouble();
                    double b = sc.nextDouble();
                    double E = sc.nextDouble();

                    if(a > b){
                        double tmp = b;
                        b = a;
                        a = tmp;
                    }

                    chordMethod(a, b, func, dfunc, E);
                    gc.buildGraph(func, a, b);

                }catch (FileNotFoundException e){
                    System.err.println("Файл не найден!");
                }
                break;

            case 2:
                try {
                    System.out.println("Введите левую границу:");
                    double a = scn.nextDouble();
                    System.out.println("Введите правую границу:");
                    double b = scn.nextDouble();
                    System.out.println("Введите желаемую погрешность:");
                    double E = scn.nextDouble();

                    if(a > b){
                        double tmp = b;
                        b = a;
                        a = tmp;
                    }

                    chordMethod(a, b, func, dfunc, E);
                    gc.buildGraph(func, a, b);

                } catch (InputMismatchException e) {
                    System.err.println("Введите числовой значение!\n");
                    scn.nextLine();
                    chordMethodSolver(func, dfunc);

                }
                break;
            default:
                System.err.println("Вы ввели неверное значение!");
                chordMethodSolver(func, dfunc);
        }
    }

    public void newtonMethodSolver(Function<Double, Double> func, Function<Double, Double> dfunc){
        System.out.println("Каким образом будете вводить данные?\n");
        System.out.println("1. Из файла");
        System.out.println("2. Вручную");

        switch (scn.nextInt()) {
            case 1:

                System.out.println(ANSI_CYAN + "Некоторые особенности чтения файлов:" + ANSI_RESET);
                System.out.println("Файл должен содержать начальное значение(x0), правую границу(x1), желаемую погрешность(E).");
                System.out.println("Все числа должны быть записаны через пробел.\n");
                System.out.println("Поместите файл в папку src и введите название файла:");
                String path = "E:\\CompMath_Lab3_NonlinearEquations-master\\src\\" + scn.next();
                try {
                    Scanner sc = new Scanner(new File(path));

                    double x0 = sc.nextDouble();
                    double x1 = sc.nextDouble();
                    double E = sc.nextDouble();
                    newtonMethod(x0, x1, func, dfunc, E);
                    gc.buildGraph(func, x0, x1);

                } catch (FileNotFoundException e) {
                    System.err.println("Файл не найден!");
                }
                break;

            case 2:
                try {
                    System.out.println("Введите начальное значение x0:");
                    double x0 = scn.nextDouble();
                    System.out.println("Введите правую границу x1:");
                    double x1 = scn.nextDouble();
                    System.out.println("Введите желаемую погрешность:");
                    double E = scn.nextDouble();

                    newtonMethod(x0, x1, func, dfunc, E);
                    gc.buildGraph(func, x0, x1);

                } catch (InputMismatchException e) {
                    System.err.println("Введите числовое значение!\n");
                    scn.nextLine();
                    newtonMethodSolver(func, dfunc);
                }
                break;
            default:
                System.err.println("Вы ввели неверное значение!");
                newtonMethodSolver(func, dfunc);
        }

    }

    public void simpleIterationsSolver(Function<Double, Double> func, Function<Double, Double> dfunc){
        System.out.println("Каким образом будете вводить данные?\n");
        System.out.println("1. Из файла");
        System.out.println("2. Вручную");

        switch (scn.nextInt()) {
            case 1:
                System.out.println(ANSI_CYAN + "Некоторые особенности чтения файлов:" + ANSI_RESET);
                System.out.println("Файл должен содержать левую границу(x0), правую границу(x1), желаемую погрешность(E).");
                System.out.println("Все числа должны быть записаны через пробел.\n");
                System.out.println("Поместите файл в папку src и введите название файла:");
                String path = "PATH TO src" + scn.next();
                try {
                    Scanner sc = new Scanner(new File(path));

                    double x0 = sc.nextDouble();
                    double x1 = sc.nextDouble();
                    double E = sc.nextDouble();

                    if(x0 > x1){
                        double tmp = x1;
                        x1 = x0;
                        x0 = tmp;
                    }

                    simpleIterationsMethod(x0, x1, func, dfunc, E);
                    gc.buildGraph(func, x0, x1);

                } catch (FileNotFoundException e) {
                    System.err.println("Файл не найден!");
                }
                break;

            case 2:
                try {
                    System.out.println("Введите левую границу:");
                    double x0 = scn.nextDouble();
                    System.out.println("Введите правую границу:");
                    double x1 = scn.nextDouble();
                    System.out.println("Введите желаемую погрешность:");
                    double E = scn.nextDouble();

                    if(x0 > x1){
                        double tmp = x1;
                        x1 = x0;
                        x0 = tmp;
                    }

                    simpleIterationsMethod(x0, x1, func, dfunc, E);
                    gc.buildGraph(func, x0, x1);

                } catch (InputMismatchException e) {
                    System.err.println("Введите числовое значение!\n");
                    scn.nextLine();
                    simpleIterationsSolver(func, dfunc);
                }
                break;
            default:
                System.err.println("Вы ввели неверное значение!");
                simpleIterationsSolver(func, dfunc);
        }
    }

    private static boolean singleRootCheck(Function<Double, Double> func, Function<Double, Double> dfunc, double a, double b){
        //a, b -  границы функции, func - функция, dFunc - производная функциии

        if(Math.signum(func.apply(a))*Math.signum(func.apply(b)) > 0) {
            System.err.println("Найти единственный корень не удалось! Знаки функций различны на краях отрезка!");
            return false;
        }

        double sign = Math.signum(dfunc.apply(a));
        double step = (b - a) / 1000;
        for(; a < b; a += step ){
            if(Math.signum(dfunc.apply(a)) != sign) {
                System.err.println("Найти единственный корень не удалось! Производная функции не сохраняет знак внутри отрезка!");
                return false;
            }
        }
        return true;
    }

}



