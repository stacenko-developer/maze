package backend.academy.mazeTask.maze.solver;

import backend.academy.mazeTask.enums.CellType;
import backend.academy.mazeTask.exception.CoordinateOutOfRangeException;
import backend.academy.mazeTask.maze.Coordinate;
import backend.academy.mazeTask.maze.Maze;
import backend.academy.mazeTask.maze.solver.dto.Cell;
import backend.academy.mazeTask.maze.solver.dto.Node;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Реализует алгоритм поиска кратчайшего пути в лабиринте с использованием
 * алгоритма поиска в ширину (BFS - Breadth-First Search).
 */
public class BfsMazeSolver extends MazeSolver {

    /**
     * Решает задачу поиска кратчайшего пути от точки старта до точки финиша в лабиринте,
     * используя алгоритм BFS.
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
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate finish) {
        super.validateData(maze, start, finish);

        return solveProcess(maze, start, finish);
    }

    private List<Coordinate> solveProcess(Maze maze, Coordinate start, Coordinate finish) {
        final CellType[][] grid = maze.grid();

        if (grid[start.x()][start.y()] == CellType.WALL || grid[finish.x()][finish.y()] == CellType.WALL) {
            return new ArrayList<>();
        }

        if (start.equals(finish)) {
            return List.of(start);
        }

        final Cell startCell = new Cell(start, grid[start.x()][start.y()]);
        final Queue<Node> queue = new LinkedList<>();

        List<Cell> shortestPath = new ArrayList<>();

        queue.add(new Node(startCell, List.of(startCell)));

        while (!queue.isEmpty()) {
            final Node node = queue.poll();
            final Coordinate current = node.cell().coordinate();

            if (current.equals(finish)) {
                if (shortestPath.isEmpty() || getWayWeight(node.path()) < getWayWeight(shortestPath)) {
                    shortestPath = new ArrayList<>(node.path());
                }
            } else {
                for (Coordinate direction : DIRECTIONS) {
                    final int newX = current.x() + direction.x();
                    final int newY = current.y() + direction.y();
                    final Coordinate newCoordinate = new Coordinate(newX, newY);

                    if (isWithinBounds(newX, newY, grid)
                        && isValidMove(newX, newY, grid)
                        && node.path().stream().noneMatch(el -> el.coordinate().equals(newCoordinate))) {

                        final List<Cell> newPath = new ArrayList<>(node.path());
                        final Cell newCell = new Cell(newCoordinate, grid[newX][newY]);

                        newPath.add(newCell);
                        queue.add(new Node(newCell, newPath));
                    }
                }
            }
        }

        return shortestPath.stream().map(Cell::coordinate).toList();
    }
}
