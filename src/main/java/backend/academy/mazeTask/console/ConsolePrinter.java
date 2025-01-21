package backend.academy.mazeTask.console;

import backend.academy.mazeTask.maze.Coordinate;
import backend.academy.mazeTask.maze.Maze;
import backend.academy.mazeTask.maze.renderer.Scene;
import java.io.PrintStream;
import java.util.List;
import lombok.experimental.UtilityClass;

/**
 * Предоставляет функции для взаимодействия с пользователем через консоль,
 * включая вывод меню выбора операций, отображение лабиринтов и результатов их решения.
 */
@UtilityClass
public class ConsolePrinter {

    private static final String NOT_GENERATED_TEXT = "не сгенерирован";
    private static final String EXIT_TO_MAIN_MENU_TEXT = "Вернуться в главное меню";

    private static final PrintStream OUTPUT = System.out;
    private static final Scene SCENE = new Scene();

    /**
     * Печатает главное меню программы.
     * Если лабиринт не был сгенерирован, отображается текст "не сгенерирован".
     *
     * @param maze объект лабиринта, который будет отображен в меню
     */
    public static void printMainMenu(Maze maze) {
        final String mainMenuFormat = "%s%n%s%n";
        final String mainMenuText = "Программа для работы с лабиринтами.";
        final String mazeText = "Лабиринт: ";
        final List<String> menuOptions = List.of(
            "Выход",
            "Сгенерировать лабиринт с помощью алгоритма Краскала",
            "Сгенерировать лабиринт с помощью алгоритма Прима",
            "Найти самый короткий путь"
        );

        OUTPUT.format(mainMenuFormat, mainMenuText, mazeText);

        if (maze == null) {
            OUTPUT.println(NOT_GENERATED_TEXT);
        } else {
            final String mazeFormat = "%s%n%s%n%n";

            OUTPUT.format(mazeFormat, SCENE.renderMaze(maze), SCENE.renderMazeDescription());
        }

        printMenuOptions(menuOptions);
        printInputYourChoice();
    }

    /**
     * Печатает меню после завершения генерации лабиринта.
     */
    public static void printFinishMazeGenerationMenu(Maze maze) {
        if (maze == null) {
            return;
        }

        final String finishMazeGenerationText = "Генерация лабиринта завершена. Сгенерированный лабиринт: %n%s%n%s%n%n";
        final List<String> menuOptions = List.of(
            EXIT_TO_MAIN_MENU_TEXT,
            "Сгенерировать снова"
        );

        OUTPUT.format(finishMazeGenerationText, SCENE.renderMaze(maze), SCENE.renderMazeDescription());

        printMenuOptions(menuOptions);
        printInputYourChoice();
    }

    /**
     * Печатает меню после завершения поиска кратчайшего пути.
     */
    public static void printFinishFindingShortestWay() {
        final String finishMazeGenerationText = "Поиск короткого пути завершен";
        final List<String> menuOptions = List.of(
            EXIT_TO_MAIN_MENU_TEXT,
            "Найти короткий путь снова"
        );

        OUTPUT.println(finishMazeGenerationText);

        printMenuOptions(menuOptions);
        printInputYourChoice();
    }

    /**
     * Запрашивает у пользователя ввод высоты лабиринта.
     */
    public static void printChooseMazeHeight() {
        OUTPUT.println("Введите высоту лабиринта:");
    }

    /**
     * Запрашивает у пользователя ввод ширины лабиринта.
     */
    public static void printChooseMazeWidth() {
        OUTPUT.println("Введите ширину лабиринта:");
    }

    /**
     * Печатает сообщение об ошибке.
     *
     * @param errorText текст ошибки, который будет выведен
     * @param decisionText текст того, что нужно делать, чтобы данной ошибки не было
     */
    public static void printErrorText(String errorText, String decisionText) {
        final String errorTextFormat = "Обнаружена ошибка: %s%n%s%n";

        OUTPUT.format(errorTextFormat, errorText, decisionText);
    }

    /**
     * Печатает сообщение об ошибке.
     *
     * @param errorText текст ошибки, который будет выведен
     */
    public static void printErrorText(String errorText) {
        final String errorTextFormat = "Обнаружена ошибка: %s%n";

        OUTPUT.format(errorTextFormat, errorText);
    }

    /**
     * Печатает лабиринт с кратчайшим найденным путем с помощью алгоритма DFS
     *
     * @param maze объект лабиринта
     * @param path список координат, представляющий кратчайший путь
     */
    public static void printMazeWithTheShortestPathDfs(Maze maze, List<Coordinate> path) {
        final String dfsAlgorithmName = "DFS";

        printMazeWithTheShortestPath(dfsAlgorithmName, maze, path);
    }

    /**
     * Печатает лабиринт с кратчайшим найденным путем с помощью алгоритма BFS
     *
     * @param maze объект лабиринта
     * @param path список координат, представляющий кратчайший путь
     */
    public static void printMazeWithTheShortestPathBfs(Maze maze, List<Coordinate> path) {
        final String bfsAlgorithmName = "BFS";

        printMazeWithTheShortestPath(bfsAlgorithmName, maze, path);
    }

    /**
     * Запрашивает у пользователя ввод начальной координаты.
     */
    public static void printChooseStartCoordinate() {
        OUTPUT.println("Выбор начальной координаты ");
    }

    /**
     * Запрашивает у пользователя ввод конечной координаты.
     */
    public static void printChooseFinishCoordinate() {
        OUTPUT.println("Выбор конечной координаты ");
    }

    /**
     * Запрашивает у пользователя ввод координаты x.
     */
    public static void printChooseXCoordinate() {
        OUTPUT.println("Введите значение координаты x: ");
    }

    /**
     * Запрашивает у пользователя ввод координаты y.
     */
    public static void printChooseYCoordinate() {
        OUTPUT.println("Введите значение координаты y: ");
    }

    /**
     * Печатает запрос на ввод выбора пользователя в меню.
     */
    private static void printInputYourChoice() {
        OUTPUT.println("Введите ваш выбор: ");
    }

    /**
     * Печатает лабиринт с кратчайшим найденным путем
     *
     * @param algorithmName название алгоритма
     * @param maze объект лабиринта
     * @param path список координат, представляющий кратчайший путь
     */
    private static void printMazeWithTheShortestPath(String algorithmName, Maze maze, List<Coordinate> path) {
        if (maze == null || path == null) {
            return;
        }

        final String shortestPathFormat = "Самый короткий путь согласно алгоритму %s: %n%s%n%s%n%s%n";

        OUTPUT.format(shortestPathFormat,
            algorithmName,
            SCENE.renderMaze(maze, path),
            SCENE.renderMazeDescription(),
            SCENE.renderPathDescription(!path.isEmpty())
        );
    }

    /**
     * Печатает пункты меню, используя нумерацию.
     *
     * @param menuOptions список опций меню, который будет выведен
     */
    private static void printMenuOptions(List<?> menuOptions) {
        if (menuOptions == null) {
            return;
        }

        final String format = "%d - %s%n";

        for (int index = 0; index < menuOptions.size(); index++) {
            OUTPUT.format(format, index, menuOptions.get(index));
        }
    }
}
