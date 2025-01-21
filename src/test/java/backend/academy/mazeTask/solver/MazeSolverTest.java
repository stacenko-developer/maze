package backend.academy.mazeTask.solver;

import backend.academy.mazeTask.CommonTest;
import backend.academy.mazeTask.enums.CellType;
import backend.academy.mazeTask.exception.CoordinateOutOfRangeException;
import backend.academy.mazeTask.maze.Coordinate;
import backend.academy.mazeTask.maze.Maze;
import backend.academy.mazeTask.maze.solver.MazeSolver;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.mazeTask.constants.ConstValues.MAX_MAZE_HEIGHT;
import static backend.academy.mazeTask.constants.ConstValues.MAX_MAZE_WIDTH;
import static backend.academy.mazeTask.constants.ConstValues.MIN_MAZE_HEIGHT;
import static backend.academy.mazeTask.constants.ConstValues.MIN_MAZE_WIDTH;
import static backend.academy.mazeTask.constants.ExceptionTextValues.COORDINATE_OUT_OF_RANGE_EXCEPTION_TEXT;
import static backend.academy.mazeTask.constants.ExceptionTextValues.NULL_COORDINATE_EXCEPTION_TEXT;
import static backend.academy.mazeTask.constants.ExceptionTextValues.NULL_MAZE_EXCEPTION_TEXT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class MazeSolverTest extends CommonTest {

    private static final Coordinate DEFAULT_COORDINATE = new Coordinate(DEFAULT_X, DEFAULT_Y);
    private static final Maze DEFAULT_MAZE = new Maze(DEFAULT_HEIGHT, DEFAULT_WIDTH, getRandomGrid(DEFAULT_HEIGHT, DEFAULT_WIDTH));

    protected abstract MazeSolver getMazeSolver();

    @ParameterizedTest
    @MethodSource("getArgumentsForFindShortestWay")
    public void solveMaze_ShouldSolveMaze(CellType[][] grid, Coordinate start, Coordinate finish, List<Coordinate> correctPath) {
        final Maze maze = new Maze(grid.length, grid[0].length, grid);
        final List<Coordinate> result = getMazeSolver().solve(maze, start, finish);

        assertEquals(result, correctPath);
    }

    @Test
    public void solveMazeWithNullMaze_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            getMazeSolver().solve(null, DEFAULT_COORDINATE, DEFAULT_COORDINATE);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_MAZE_EXCEPTION_TEXT);
    }

    @Test
    public void solveMazeWithNullStartCoordinate_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            getMazeSolver().solve(DEFAULT_MAZE, null, DEFAULT_COORDINATE);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_COORDINATE_EXCEPTION_TEXT);
    }

    @Test
    public void solveMazeWithNullEndCoordinate_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            getMazeSolver().solve(DEFAULT_MAZE, DEFAULT_COORDINATE, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_COORDINATE_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForSolveMazeWithOutOufRangeXCoordinate")
    public void solveMazeWithOutOfRangeFirstXCoordinate_ShouldThrowCoordinateOutOfRangeException(int height, int incorrectX) {
        assertThatThrownBy(() -> {
            getMazeSolver().solve(getRandomMaze(height, DEFAULT_WIDTH), new Coordinate(incorrectX, DEFAULT_Y), DEFAULT_COORDINATE);
        }).isInstanceOf(CoordinateOutOfRangeException.class)
            .hasMessageContaining(COORDINATE_OUT_OF_RANGE_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForSolveMazeWithOutOufRangeYCoordinate")
    public void solveMazeWithOutOfRangeFirstYCoordinate_ShouldThrowCoordinateOutOfRangeException(int width, int incorrectY) {
        assertThatThrownBy(() -> {
            getMazeSolver().solve(getRandomMaze(DEFAULT_HEIGHT, width), new Coordinate(DEFAULT_X, incorrectY), DEFAULT_COORDINATE);
        }).isInstanceOf(CoordinateOutOfRangeException.class)
            .hasMessageContaining(COORDINATE_OUT_OF_RANGE_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForSolveMazeWithOutOufRangeXCoordinate")
    public void solveMazeWithOutOfRangeSecondXCoordinate_ShouldThrowCoordinateOutOfRangeException(int height, int incorrectX) {
        assertThatThrownBy(() -> {
            getMazeSolver().solve(getRandomMaze(height, DEFAULT_WIDTH), DEFAULT_COORDINATE, new Coordinate(incorrectX, DEFAULT_Y));
        }).isInstanceOf(CoordinateOutOfRangeException.class)
            .hasMessageContaining(COORDINATE_OUT_OF_RANGE_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForSolveMazeWithOutOufRangeYCoordinate")
    public void solveMazeWithOutOfRangeSecondYCoordinate_ShouldThrowCoordinateOutOfRangeException(int width, int incorrectY) {
        assertThatThrownBy(() -> {
            getMazeSolver().solve(getRandomMaze(DEFAULT_HEIGHT, width), DEFAULT_COORDINATE, new Coordinate(DEFAULT_X, incorrectY));
        }).isInstanceOf(CoordinateOutOfRangeException.class)
            .hasMessageContaining(COORDINATE_OUT_OF_RANGE_EXCEPTION_TEXT);
    }

    private static List<Object[]> getArgumentsForSolveMazeWithOutOufRangeXCoordinate() {
        return getArgumentsForSolveMazeWithOutOufRangeCoordinate(MIN_MAZE_HEIGHT, MAX_MAZE_HEIGHT);
    }

    private static List<Object[]> getArgumentsForSolveMazeWithOutOufRangeYCoordinate() {
        return getArgumentsForSolveMazeWithOutOufRangeCoordinate(MIN_MAZE_WIDTH, MAX_MAZE_WIDTH);
    }


    private static List<Object[]> getArgumentsForSolveMazeWithOutOufRangeCoordinate(int minSize, int maxSize) {
        final List<Object[]> result = new ArrayList<>();

        for (int size = minSize; size <= maxSize; size++) {
            final List<Pair<Integer, Integer>> ranges = List.of(
                new Pair<>(-50, 0),
                new Pair<>(size, size + 50)
            );

            for (Pair<Integer, Integer> range : ranges) {
                for (int j = range.getKey(); j < range.getValue(); j++) {
                    result.add(new Object[]{size, j});
                }
            }
        }

        return result;
    }

}
