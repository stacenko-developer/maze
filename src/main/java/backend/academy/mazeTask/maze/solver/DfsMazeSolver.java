package backend.academy.mazeTask.maze.solver;

import backend.academy.mazeTask.enums.CellType;
import backend.academy.mazeTask.exception.CoordinateOutOfRangeException;
import backend.academy.mazeTask.maze.Coordinate;
import backend.academy.mazeTask.maze.Maze;
import backend.academy.mazeTask.maze.solver.dto.Cell;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализует алгоритм поиска кратчайшего пути в лабиринте
 * с использованием поиска в глубину (DFS - Depth-First Search).
 */
public class DfsMazeSolver extends MazeSolver {

    private CellType[][] grid;
    private List<Cell> shortestPath;

    /**
     * Решает задачу поиска кратчайшего пути от точки старта до точки финиша в лабиринте,
     * используя алгоритм DFS.
     *
     * @param maze лабиринт, представленный объектом {@link Maze}, в котором необходимо найти путь
     * @param start начальная координата пути
     * @param finish конечная координата пути
     *
     * @return список координат, представляющий кратчайший путь от начальной точки до конечной.
     * Если путь не найден, возвращается пустой список.
     * Если начальная и конечная точки совпадают, возвращается список с одной точкой.
     *
     * @throws NullPointerException если входные данные являются null
     * @throws CoordinateOutOfRangeException если координата начала или конца вышла за пределы лабиринта
     */
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate finish) {
        super.validateData(maze, start, finish);

        return solveProcess(maze, start, finish);
    }

    private List<Coordinate> solveProcess(Maze maze, Coordinate start, Coordinate finish) {
        this.grid = maze.grid();

        if (grid[start.x()][start.y()] == CellType.WALL || grid[finish.x()][finish.y()] == CellType.WALL) {
            return new ArrayList<>();
        }

        if (start.equals(finish)) {
            return List.of(start);
        }

        this.shortestPath = new ArrayList<>();

        final CellType startCoordinateCellType = grid[start.x()][start.y()];
        final List<Cell> path = new ArrayList<>();
        path.add(new Cell(start, startCoordinateCellType));

        findShortestPath(start, finish, null, path);
        return shortestPath.stream().map(Cell::coordinate).toList();
    }

    private void findShortestPath(Coordinate current, Coordinate finish, Coordinate prev, List<Cell> path) {
        if (current.equals(finish)) {
            if (shortestPath.isEmpty() || getWayWeight(path) < getWayWeight(shortestPath)) {
                shortestPath = new ArrayList<>(path);
            }

            return;
        }

        for (Coordinate direction : DIRECTIONS) {
            final int nextX = current.x() + direction.x();
            final int nextY = current.y() + direction.y();

            if (isWithinBounds(nextX, nextY, grid)) {
                final CellType cellType = grid[nextX][nextY];
                final Coordinate newCoordinate = new Coordinate(nextX, nextY);

                if (isValidMove(nextX, nextY, grid)
                    && !(prev != null && nextX == prev.x() && nextY == prev.y())
                    && path.stream().noneMatch(cell -> cell.coordinate().equals(newCoordinate))) {
                    path.add(new Cell(newCoordinate, cellType));

                    findShortestPath(newCoordinate, finish, current, path);

                    path.removeLast();
                }
            }
        }
    }
}
