package backend.academy.mazeTask.maze;

import backend.academy.mazeTask.console.ConsolePrinter;
import backend.academy.mazeTask.console.ConsoleReader;
import backend.academy.mazeTask.exception.MazeNotGeneratedException;
import backend.academy.mazeTask.maze.generator.KruskalMazeGenerator;
import backend.academy.mazeTask.maze.generator.MazeGenerator;
import backend.academy.mazeTask.maze.generator.PrimMazeGenerator;
import backend.academy.mazeTask.maze.solver.BfsMazeSolver;
import backend.academy.mazeTask.maze.solver.DfsMazeSolver;
import java.util.List;
import static backend.academy.mazeTask.constants.ConstValues.MAX_MAZE_HEIGHT;
import static backend.academy.mazeTask.constants.ConstValues.MAX_MAZE_WIDTH;
import static backend.academy.mazeTask.constants.ConstValues.MIN_MAZE_HEIGHT;
import static backend.academy.mazeTask.constants.ConstValues.MIN_MAZE_WIDTH;
import static backend.academy.mazeTask.constants.ExceptionTextValues.MAZE_NOT_GENERATED_DECISION;
import static backend.academy.mazeTask.constants.ExceptionTextValues.UNKNOWN_ERROR_EXCEPTION_TEXT;

/**
 * Реализует логику пользовательского меню для управления генерацией лабиринтов и поиска кратчайших путей.
 * Включает возможности генерации лабиринтов с использованием алгоритмов Краскала и Прима,
 * а также поиска кратчайшего пути с помощью алгоритмов DFS и BFS.
 */
public class MazeMenu {

    private final MazeGenerator kruskalMazeGenerator;
    private final MazeGenerator primMazeGenerator;
    private final DfsMazeSolver dfsMazeSolver = new DfsMazeSolver();
    private final BfsMazeSolver bfsMazeSolver = new BfsMazeSolver();

    private int height;
    private int width;
    private Coordinate start;
    private Coordinate finish;

    private Maze maze = null;

    public MazeMenu() {
        this(null, null);
    }

    public MazeMenu(MazeGenerator kruskalMazeGenerator, MazeGenerator primMazeGenerator) {
        this.kruskalMazeGenerator = kruskalMazeGenerator != null
            ? kruskalMazeGenerator
            : new KruskalMazeGenerator();

        this.primMazeGenerator = primMazeGenerator != null
            ? primMazeGenerator
            : new PrimMazeGenerator();
    }

    /**
     * Запускает основной цикл программы, который отображает меню,
     * обрабатывает выборы пользователя и выполняет соответствующие действия.
     * Завершает работу, если пользователь выбрал опцию "Выход".
     */
    public void start() {
        ConsolePrinter.printMainMenu(maze);

        final int minMenuOptionNumber = 0;
        final int maxMenuOptionNumber = 3;

        while (true) {
            try {
                final int menu = ConsoleReader.readPositiveInteger(minMenuOptionNumber, maxMenuOptionNumber);

                if (menu == minMenuOptionNumber) {
                    break;
                }

                readMenuOption(menu);
            } catch (MazeNotGeneratedException ex) {
                ConsolePrinter.printErrorText(ex.getMessage(), MAZE_NOT_GENERATED_DECISION);
            } catch (RuntimeException ex) {
                ConsolePrinter.printErrorText(UNKNOWN_ERROR_EXCEPTION_TEXT);
            }
        }
    }

    /**
     * Обрабатывает выбор пользователя из меню.
     * Поддерживаются следующие опции:
     * - 0: Выход
     * - 1: Генерация лабиринта по алгоритму Краскала
     * - 2: Генерация лабиринта по алгоритму Прима
     * - 3: Поиск кратчайшего пути (сначала с использованием DFS, затем BFS)
     *
     */
    private void readMenuOption(int menuOption) {
        final int generateMazeByKruskalOption = 1;
        final int generateMazeByPrimOption = 2;
        final int findTheShortestWayOption = 3;

        switch (menuOption) {
            case generateMazeByKruskalOption:
                generateMaze(kruskalMazeGenerator);
                break;
            case generateMazeByPrimOption:
                generateMaze(primMazeGenerator);
                break;
            case findTheShortestWayOption:
                findShortestPath();
                break;
            default:
                break;
        }
    }

    /**
     * Генерирует лабиринт с помощью указанного генератора и сохраняет его.
     *
     * @param mazeGenerator генератор лабиринтов (например, KruskalMazeGenerator или PrimMazeGenerator).
     */
    private void generateMaze(MazeGenerator mazeGenerator) {
        final int minMenuOptionNumber = 0;
        final int maxMenuOptionNumber = 1;

        int menu;

        do {
            chooseMazeSize();
            maze = mazeGenerator.generate(height, width);
            ConsolePrinter.printFinishMazeGenerationMenu(maze);
            menu = ConsoleReader.readPositiveInteger(minMenuOptionNumber, maxMenuOptionNumber);
        } while (menu != minMenuOptionNumber);

        ConsolePrinter.printMainMenu(maze);
    }

    /**
     * Находит кратчайший путь в лабиринте с использованием DFS и BFS и считывает начальную и конечную точки.
     *
     * @throws MazeNotGeneratedException если лабиринт не был сгенерирован перед попыткой поиска пути.
     */
    private void findShortestPath() {
        if (maze == null) {
            throw new MazeNotGeneratedException();
        }

        final int minMenuOptionNumber = 0;
        final int maxMenuOptionNumber = 1;

        int menu;

        do {
            chooseCoordinates();
            findShortestPathDfs();
            findShortestPathBfs();
            ConsolePrinter.printFinishFindingShortestWay();
            menu = ConsoleReader.readPositiveInteger(minMenuOptionNumber, maxMenuOptionNumber);
        } while (menu != minMenuOptionNumber);

        ConsolePrinter.printMainMenu(maze);
    }

    /**
     * Находит кратчайший путь в лабиринте с использованием алгоритма DFS.
     */
    private void findShortestPathDfs() {
        final List<Coordinate> shortestPathDfs = dfsMazeSolver.solve(maze, start, finish);
        ConsolePrinter.printMazeWithTheShortestPathDfs(maze, shortestPathDfs);
    }

    /**
     * Находит кратчайший путь в лабиринте с использованием алгоритма BFS.
     */
    private void findShortestPathBfs() {
        final List<Coordinate> shortestPathBfs = bfsMazeSolver.solve(maze, start, finish);
        ConsolePrinter.printMazeWithTheShortestPathBfs(maze, shortestPathBfs);
    }

    /**
     * Метод для выбора размеров лабиринта пользователем.
     * Запрашивает у пользователя высоту и ширину лабиринта в пределах допустимых значений.
     *
     */
    private void chooseMazeSize() {
        ConsolePrinter.printChooseMazeHeight();
        height = ConsoleReader.readPositiveInteger(MIN_MAZE_HEIGHT, MAX_MAZE_HEIGHT);
        ConsolePrinter.printChooseMazeWidth();
        width = ConsoleReader.readPositiveInteger(MIN_MAZE_WIDTH, MAX_MAZE_WIDTH);
    }

    /**
     * Метод для выбора начальных и конечных координат лабиринта для поиска кратчайшего пути.
     * Пользователь вводит координаты x и y для начальной и конечной точек.
     */
    private void chooseCoordinates() {
        ConsolePrinter.printChooseStartCoordinate();
        start = readCoordinate();
        ConsolePrinter.printChooseFinishCoordinate();
        finish = readCoordinate();
    }

    /**
     * Читает координаты x и y, введенные пользователем, и возвращает координату.
     *
     * @return считанную координату.
     *
     */
    private Coordinate readCoordinate() {
        final int minValue = 0;
        final int maxX = height - 1;
        final int maxY = width - 1;
        final int x;
        final int y;

        ConsolePrinter.printChooseXCoordinate();
        x = ConsoleReader.readPositiveInteger(minValue, maxX);
        ConsolePrinter.printChooseYCoordinate();
        y = ConsoleReader.readPositiveInteger(minValue, maxY);

        return new Coordinate(x, y);
    }
}
