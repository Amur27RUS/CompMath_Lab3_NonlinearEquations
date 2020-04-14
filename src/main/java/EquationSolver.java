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

    private void chordMethod(double a, double b, Function<Double, Double> func, double E){
        //Метод Хорд
        // a, b - пределы хорды, func - функция, E - необходимая погрешность

        int count = 0;
        double x;

        if(func.apply(a)*func.apply(b) >=0){
            System.out.println("Корней нет!");
        }else {

            System.out.println("Как вы хотите вывести решение?");
            System.out.println("1. В файл");
            System.out.println("2. В консоль");

            boolean fileOut = (scn.nextInt() == 1);

            if(fileOut){
                System.out.println("Поместите файл в папку src и введите название файла:");
                String path = "YOUR PATH TO THE SRC FOLDER HERE" + scn.next();
                try {
                    fos = new FileOutputStream(path);
                    filePrintStream = new PrintStream(fos);
                }catch (IOException e){
                    System.err.println("Ошибка чтения файла!");
                }
            }


            if(fileOut){
                    filePrintStream.printf("%15s %15s %15s %15s %15s %15s %15s %15s %n",
                            "Шаг", "a", "b", "x", "func(a)", "func(b)", "func(x)", "|b - a|");
            }else {
                System.out.printf("%15s %15s %15s %15s %15s %15s %15s %15s %n",
                        "Шаг", "a", "b", "x", "func(a)", "func(b)", "func(x)", "|b - a|");
            }

            while (Math.abs(func.apply(b) - func.apply(a)) > E) {
                count++;
                
                double c = (func.apply(b) * a - func.apply(a) * b) / (func.apply(b) - func.apply(a));
                
                if(func.apply(a) * func.apply(c) > 0) a = c;
                    else b =c;
                    
                if(fileOut){
                    filePrintStream.printf("%15d %15f %15f %15f %15f %15f %15f %15f %n",
                            count, a, b, c, func.apply(a), func.apply(b), func.apply(c), Math.abs(b - a));
                }else {
                    System.out.printf("%15d %15f %15f %15f %15f %15f %15f %15f %n",
                            count, a, b, c, func.apply(a), func.apply(b), func.apply(c), Math.abs(b - a));
                }
            }
            if(fileOut){
                    filePrintStream.close();
            }
        }
    }

    private void newtonMethod(double x0, Function<Double, Double> func, Function<Double, Double> dFunc, double E){
        //Метод Ньютона
        //x0 - начальное значение, func - функция, dFunc- производная функции, E - необходимая погрешность
        int count = 0;

        System.out.println("Как вы хотите вывести решение?");
        System.out.println("1. В файл");
        System.out.println("2. В консоль");

        boolean fileOut = (scn.nextInt() == 1);

        if(fileOut){
            System.out.println("Поместите файл в папку src и введите название файла:");
            String path = "YOUR PATH TO THE SRC FOLDER HERE" + scn.next();
            try {
                fos = new FileOutputStream(path);
                filePrintStream = new PrintStream(fos);
            }catch (IOException e){
                System.err.println("Ошибка чтения файла!");
            }
        }

        double x = x0 - (func.apply(x0) / dFunc.apply(x0));

        if(fileOut){
            filePrintStream.printf("%15s %15s %15s %15s %15s %15s %n",
                    "Шаг", "Xn", "func(Xn)", "dfunc(Xn)", "Xn+1", "|Xn+1 - Xn|");
        }else {
            System.out.printf("%15s %15s %15s %15s %15s %15s %n",
                    "Шаг", "Xn", "func(Xn)", "dfunc(Xn)", "Xn+1", "|Xn+1 - Xn|");
        }

        while(Math.abs(x-x0) > E){
            count ++;

            x0 = x;
            x =x0 - (func.apply(x0) / dFunc.apply(x0));

            if(fileOut){
                filePrintStream.printf("%15d %15f %15f %15f %15f %15f %n",
                        count, x0, func.apply(x), dFunc.apply(x), x, Math.abs(x - x0));
            }else {
                System.out.printf("%15d %15f %15f %15f %15f %15f %n",
                        count, x0, func.apply(x), dFunc.apply(x), x, Math.abs(x - x0));
            }
        }
        filePrintStream.close();
    }

    private void simpleIterationsMethod(double x0, double x1, Function<Double, Double> func, Function<Double, Double> dFunc, double E){
        //x0, x1- начальное приближение, func - функция, dFunc - произовдная функции, E - необходимая погрешность

        int count = 0;



        double lambda = 1 / dFunc.apply(x1);

        if((x0 - lambda*func.apply(x0))*(x1 - lambda*func.apply(x1)) > 0){
            System.out.println("Решений нет!");
        }else{

            System.out.println("Как вы хотите вывести решение?");
            System.out.println("1. В файл");
            System.out.println("2. В консоль");

            boolean fileOut = (scn.nextInt() == 1);

            if(fileOut){
                System.out.println("Поместите файл в папку src и введите название файла:");
                String path = "YOUR PATH TO THE SRC FOLDER HERE" + scn.next();
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

            double x = x0 - lambda * func.apply(x0);

            while (Math.abs(x-x0) > E){
                count++;
                x0 = x;
                x = x0 - lambda * func.apply(x0);

                if(fileOut){
                    filePrintStream.printf("%15d %15f %15f %15f %15f %n",
                            count, x0, func.apply(x0), x, Math.abs(x - x0));
                }else {
                    System.out.printf("%15d %15f %15f %15f %15f %n",
                            count, x0, func.apply(x0), x, Math.abs(x - x0));
                }

            }
            filePrintStream.close();
        }
    }

    public void chordMethodSolver(Function<Double, Double> func){
        System.out.println("Каким образом будете вводить данные?\n");
        System.out.println("1. Из файла");
        System.out.println("2. Вручную");

        switch (scn.nextInt()) {
            case 1:
                System.out.println(ANSI_CYAN + "Некоторые особенности чтения файлов:" + ANSI_RESET);
                System.out.println("Файл должен содержать левую границу(a), правую границу(b), желаемую погрешность(E).");
                System.out.println("Все числа должны быть записаны через пробел.\n");
                System.out.println("Поместите файл в папку src и введите название файла:");
                String path = "YOUR PATH TO THE SRC FOLDER HERE" + scn.next();
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

                    chordMethod(a, b, func, E);
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

                    chordMethod(a, b, func, E);
                    gc.buildGraph(func, a, b);

                } catch (InputMismatchException e) {
                    System.err.println("Введите числовой значение!\n");
                    scn.nextLine();
                    chordMethodSolver(func);

                }
                break;
            default:
                System.err.println("Вы ввели неверное значение!");
                chordMethodSolver(func);
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
                String path = "YOUR PATH TO THE SRC FOLDER HERE" + scn.next();
                try {
                    Scanner sc = new Scanner(new File(path));

                    double x0 = sc.nextDouble();
                    double x1 = sc.nextDouble();
                    double E = sc.nextDouble();
                    newtonMethod(x0, func, dfunc, E);
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

                    newtonMethod(x0, func, dfunc, E);
                    gc.buildGraph(func, x0, x1);

                } catch (InputMismatchException e) {
                    System.err.println("Введите числовое значение!\n");
                    scn.nextLine();
                    newtonMethodSolver(func, dfunc);
                }
                break;
            default:
                System.err.println("Вы ввели неверное значение!");
                chordMethodSolver(func);
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
                String path = "YOUR PATH TO THE SRC FOLDER HERE" + scn.next();
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
                chordMethodSolver(func);
        }
    }
}



