package backend.academy.mazeTask.maze.solver;

import backend.academy.mazeTask.enums.CellType;
import backend.academy.mazeTask.exception.CoordinateOutOfRangeException;
import backend.academy.mazeTask.maze.Coordinate;
import backend.academy.mazeTask.maze.Maze;
import backend.academy.mazeTask.maze.solver.dto.Cell;
import java.util.List;
import static backend.academy.mazeTask.constants.ExceptionTextValues.NULL_COORDINATE_EXCEPTION_TEXT;
import static backend.academy.mazeTask.constants.ExceptionTextValues.NULL_MAZE_EXCEPTION_TEXT;

public abstract class MazeSolver {

    static final Coordinate[] DIRECTIONS = {
        new Coordinate(0, 1),
        new Coordinate(1, 0),
        new Coordinate(0, -1),
        new Coordinate(-1, 0)
    };

    protected void validateData(Maze maze, Coordinate start, Coordinate finish) {
        if (maze == null) {
            throw new NullPointerException(NULL_MAZE_EXCEPTION_TEXT);
        }

        validateCoordinate(start, maze);
        validateCoordinate(finish, maze);
    }

    protected int getWayWeight(List<Cell> path) {
        return path.stream()
            .mapToInt(cell -> cell.cellType().weight())
            .sum();
    }

    protected boolean isWithinBounds(int x, int y, CellType[][] grid) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }

    protected boolean isValidMove(int x, int y, CellType[][] grid) {
        return grid[x][y] != CellType.WALL;
    }

    private boolean isInvalidRange(Coordinate coordinate, Maze maze) {
        return coordinate.x() < 0 || coordinate.x() >= maze.height()
            || coordinate.y() < 0 || coordinate.y() >= maze.width();
    }

    private void validateCoordinate(Coordinate coordinate, Maze maze) {
        if (coordinate == null) {
            throw new NullPointerException(NULL_COORDINATE_EXCEPTION_TEXT);
        }

        if (isInvalidRange(coordinate, maze)) {
            throw new CoordinateOutOfRangeException();
        }
    }

    public abstract List<Coordinate> solve(Maze maze, Coordinate start, Coordinate finish);
}
