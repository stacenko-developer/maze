package backend.academy.mazeTask.maze.generator;

import backend.academy.mazeTask.enums.CellType;
import backend.academy.mazeTask.exception.IncorrectMazeHeightException;
import backend.academy.mazeTask.exception.IncorrectMazeWidthException;
import backend.academy.mazeTask.maze.Coordinate;
import backend.academy.mazeTask.maze.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Используется для генерации лабиринтов с использованием алгоритма Прима.
 */
public class PrimMazeGenerator extends MazeGenerator {

    /**
     * Генерирует лабиринт заданной высоты и ширины с использованием алгоритма Прима.
     *
     * @param height высота лабиринта, который нужно сгенерировать
     * @param width ширина лабиринта, который нужно сгенерировать
     *
     * @return сгенерированный лабиринт в виде объекта класса {@link Maze}
     *
     * @throws IncorrectMazeHeightException если высота лабиринта вышла за допустимые пределы.
     * @throws IncorrectMazeWidthException если ширина лабиринта вышла за допустимые пределы.
     */
    @Override
    public Maze generate(int height, int width) {
        super.validateData(height, width);

        super.height = height;
        super.width = width;

        return generateProcess();
    }

    private Maze generateProcess() {
        final CellType[][] grid = initGrid();
        final int startX = secureRandom.nextInt((height - 1) / 2) * 2 + 1;
        final int startY = secureRandom.nextInt((width - 1) / 2) * 2 + 1;

        grid[startX][startY] = getRandomPassageView();

        final List<Coordinate> coordinates = getNeighbours(grid, startX, startY, cell -> cell == CellType.WALL);

        while (!coordinates.isEmpty()) {
            final Coordinate coordinate = coordinates
                .remove(secureRandom.nextInt(coordinates.size()));
            final List<Coordinate> neighbours = getNeighbours(
                grid, coordinate.x(), coordinate.y(), cell -> cell != CellType.WALL
            );

            if (!neighbours.isEmpty()) {
                Coordinate neighbour = neighbours.get(secureRandom.nextInt(neighbours.size()));
                grid[coordinate.x()][coordinate.y()] = getRandomPassageView();
                connectCells(grid, coordinate, neighbour);
                coordinates.addAll(getNeighbours(grid, coordinate.x(), coordinate.y(), cell -> cell == CellType.WALL));
            }
        }

        setEntryExit(grid);

        return new Maze(height, width, grid);
    }

    private void connectCells(CellType[][] grid, Coordinate first, Coordinate second) {
        final int wallX = (first.x() + second.x()) / 2;
        final int wallY = (first.y() + second.y()) / 2;

        setCellType(grid, wallX, wallY, getRandomPassageView());
    }

    private List<Coordinate> getNeighbours(CellType[][] grid, int x, int y, Predicate<CellType> condition) {
        final List<Coordinate> neighbours = new ArrayList<>();
        final Coordinate[] directions = {
            new Coordinate(0, 2),
            new Coordinate(2, 0),
            new Coordinate(0, -2),
            new Coordinate(-2, 0),
        };

        for (Coordinate coordinate : directions) {
            final int newX = x + coordinate.x();
            final int newY = y + coordinate.y();
            if (isInBounds(newX, newY) && condition.test(grid[newX][newY])) {
                neighbours.add(new Coordinate(newX, newY));
            }

        }

        return neighbours;
    }

    private boolean isInBounds(int x, int y) {
        return x > 0 && x < height - 1 && y > 0 && y < width - 1;
    }

    private CellType[][] initGrid() {
        final CellType[][] grid = new CellType[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = CellType.WALL;
            }
        }

        return grid;
    }
}
