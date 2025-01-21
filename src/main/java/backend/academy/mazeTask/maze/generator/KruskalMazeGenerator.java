package backend.academy.mazeTask.maze.generator;

import backend.academy.mazeTask.enums.CellType;
import backend.academy.mazeTask.exception.IncorrectMazeHeightException;
import backend.academy.mazeTask.exception.IncorrectMazeWidthException;
import backend.academy.mazeTask.maze.Coordinate;
import backend.academy.mazeTask.maze.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Используется для генерации лабиринтов с использованием алгоритма Краскала.
 */
public class KruskalMazeGenerator extends MazeGenerator {

    /**
     * Генерирует лабиринт заданной высоты и ширины с использованием алгоритма Краскала.
     *
     * @param height высота лабиринта, который нужно сгенерировать
     * @param width высота лабиринта, который нужно сгенерировать
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
        final List<List<Coordinate>> maze = new ArrayList<>();
        final List<List<Coordinate>> cells = getCells();
        final int minSize = 1;

        Collections.shuffle(cells);

        while (cells.size() > minSize) {
            final List<Coordinate> first = cells.getFirst();
            boolean connected = false;

            for (List<Coordinate> second : cells.subList(minSize, cells.size())) {
                for (Coordinate cell : first) {
                    final List<Coordinate> neighbours = getValidNeighbours(cell, second);

                    for (Coordinate neighbour : neighbours) {
                        maze.add(List.of(cell, neighbour));
                        cells.remove(first);
                        cells.remove(second);
                        cells.add(Stream.concat(first.stream(), second.stream()).toList());
                        connected = true;
                        break;
                    }
                    if (connected) {
                        break;
                    }
                }
                if (connected) {
                    break;
                }
            }
        }

        return new Maze(height, width, getGrid(maze));
    }

    private List<List<Coordinate>> getCells() {
        final List<List<Coordinate>> cells = new ArrayList<>();

        for (int x = 0; x < height / 2; x++) {
            for (int y = 0; y < width / 2; y++) {
                cells.add(Collections.singletonList(new Coordinate(x, y)));
            }
        }

        return cells;
    }

    private CellType[][] getGrid(List<List<Coordinate>> maze) {
        final CellType[][] grid = new CellType[height][width];

        for (List<Coordinate> path : maze) {
            if (path.isEmpty()) {
                continue;
            }

            final Coordinate first = new Coordinate(path.getFirst().x() * 2 + 1, path.getFirst().y() * 2 + 1);
            final Coordinate second = new Coordinate(path.getLast().x() * 2 + 1, path.getLast().y() * 2 + 1);
            final Coordinate midPoint = new Coordinate((first.x() + second.x()) / 2, (first.y() + second.y()) / 2);

            setCellType(grid, first.x(), first.y(), getRandomPassageView());
            setCellType(grid, second.x(), second.y(), getRandomPassageView());
            setCellType(grid, midPoint.x(), midPoint.y(), getRandomPassageView());
        }

        for (int y = 0; y < width; y++) {
            setCellType(grid, 0, y, CellType.WALL);
            setCellType(grid, height - 1, y, CellType.WALL);
        }

        for (int x = 0; x < height; x++) {
            setCellType(grid, x, 0, CellType.WALL);
            setCellType(grid, x, width - 1, CellType.WALL);
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == null) {
                    grid[i][j] = CellType.WALL;
                }
            }
        }

        setEntryExit(grid);

        return grid;
    }

    private List<Coordinate> getValidNeighbours(Coordinate cell, List<Coordinate> coordinates) {
        final int x = cell.x();
        final int y = cell.y();
        final int neighboursCount = 4;
        final List<Coordinate> neighbours = new ArrayList<>(neighboursCount);

        if (x + 1 < height / 2 && coordinates.contains(new Coordinate(x + 1, y))) {
            neighbours.add(new Coordinate(x + 1, y));
        }
        if (x - 1 >= 0 && coordinates.contains(new Coordinate(x - 1, y))) {
            neighbours.add(new Coordinate(x - 1, y));
        }
        if (y + 1 < width / 2 && coordinates.contains(new Coordinate(x, y + 1))) {
            neighbours.add(new Coordinate(x, y + 1));
        }
        if (y - 1 >= 0 && coordinates.contains(new Coordinate(x, y - 1))) {
            neighbours.add(new Coordinate(x, y - 1));
        }

        Collections.shuffle(neighbours);
        return neighbours;
    }
}
