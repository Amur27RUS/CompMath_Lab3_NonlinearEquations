import java.util.Scanner;
import java.util.function.Function;

public class InputController {
    /*------Цвета для консоли-------*/
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";

    EquationSolver es = new EquationSolver();

    String[] textFuncs = {
            "x^3 - x + 4  (лекционное)",
            "x^3 + 2.28x^2 - 1.934x - 3.907  (из варианта)"
    };
    String[] methods = {
            "Метод хорд",
            "Метод Ньютона",
            "Метод простых итераций"
    };
    /*------Функции из моего варианта-------*/
    Function<Double, Double> func = x -> Math.pow(x, 3) + 2.28*Math.pow(x, 2) - 1.934 * x - 3.907;
    Function<Double, Double> dFunc = x -> 3*Math.pow(x, 2) + 4.56*x - 1.934;
    /*------Функции из лекции-------*/
    Function<Double, Double> Lecfunc = x -> Math.pow(x, 3) -x + 4;
    Function<Double, Double> LecdFunc = x -> 3*Math.pow(x, 2) - 1;

    Scanner scn = new Scanner(System.in);
    boolean exit = false;

    /*------Метод запуска-------*/
    public void start(){
            try {
            System.out.print("▄▀▄▀▄▀▄▀▄▀▄▀▄▀  ");
                Thread.sleep(1000);
            System.out.print(ANSI_CYAN + "Добро пожаловать ");
                Thread.sleep(500);
            System.out.print("в решатель");
                Thread.sleep(500);
            System.out.print(" нелинейных уравнений" + ANSI_RESET);
                Thread.sleep(500);
            System.out.println("  ▄▀▄▀▄▀▄▀▄▀▄▀▄▀");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("\n1. Начать решение уравнений");
            System.out.println("2. Выйти");

        while (!exit){
            switch (scn.nextInt()){
                case 1:
                     chooseEquasion();
                    break;
                case 2:
                    System.out.println("Завершаюсь :(");
                    exit = true;
                    System.exit(0);
                    break;
                default:
                    System.out.println("Введите правильную команду!");
                    continue;
            }
        }
    }
    /*------Выбор уравнения-------*/
    private void chooseEquasion(){
        try {
            System.out.println("Выберите уравнение:");
            Thread.sleep(1000);

            System.out.println("\n1. " + textFuncs[0]);
            System.out.println("2. " + textFuncs[1]);
            System.out.println("3. Выйти");

        }catch (InterruptedException e){
            e.printStackTrace();
        }

        switch(scn.nextInt()){
            case 1:
                chooseMethod(Lecfunc, LecdFunc);
                break;
            case 2:
                chooseMethod(func, dFunc);
                break;
            case 3:
                exit = true;
                break;
        }


    }
    /*------Выбор метода решения + запуск этого метода-------*/
    private void chooseMethod(Function<Double, Double> func, Function<Double, Double> dFunc){
        try {
            System.out.println("Выберите метод решения нелинейных уравнений:");
            Thread.sleep(1000);
            System.out.println("\n1. " + methods[0]);
            System.out.println("2. " + methods[1]);
            System.out.println("3. " + methods[2]);
            System.out.println("4. Назад");
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        switch (scn.nextInt()){
            case 1:
                es.chordMethodSolver(func);
                System.out.println("\n1. Начать решать новое уравнение?");
                System.out.println("2. Выйти");
                break;

            case 2:
                es.newtonMethodSolver(func, dFunc);
                System.out.println("\n1. Начать решать новое уравнение?");
                System.out.println("2. Выйти");
                break;

            case 3:
                es.simpleIterationsSolver(func, dFunc);
                System.out.println("\n1. Начать решать новое уравнение?");
                System.out.println("2. Выйти");
                break;

            case 4:
                chooseEquasion();
                break;
        }
    }

}
