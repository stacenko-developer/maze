package backend.academy.mazeTask;

import backend.academy.mazeTask.enums.CellType;
import backend.academy.mazeTask.exception.IncorrectMazeGridException;
import backend.academy.mazeTask.exception.IncorrectMazeHeightException;
import backend.academy.mazeTask.exception.IncorrectMazeWidthException;
import backend.academy.mazeTask.exception.MazeWithIncorrectEntryExitException;
import backend.academy.mazeTask.maze.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.mazeTask.constants.ConstValues.MAX_MAZE_HEIGHT;
import static backend.academy.mazeTask.constants.ConstValues.MAX_MAZE_WIDTH;
import static backend.academy.mazeTask.constants.ConstValues.MIN_MAZE_HEIGHT;
import static backend.academy.mazeTask.constants.ConstValues.MIN_MAZE_WIDTH;
import static backend.academy.mazeTask.constants.ExceptionTextValues.INCORRECT_ENTRY_EXIT_IN_MAZE_EXCEPTION_TEXT;
import static backend.academy.mazeTask.constants.ExceptionTextValues.INCORRECT_MAZE_GRID_EXCEPTION_TEXT;
import static backend.academy.mazeTask.constants.ExceptionTextValues.INCORRECT_MAZE_HEIGHT_EXCEPTION_TEXT;
import static backend.academy.mazeTask.constants.ExceptionTextValues.INCORRECT_MAZE_WIDTH_EXCEPTION_TEXT;
import static backend.academy.mazeTask.constants.ExceptionTextValues.NULL_MAZE_GRID_EXCEPTION_TEXT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MazeTest extends CommonTest {

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateMaze")
    public void createMaze_ShouldCreateMaze(int height, int width, CellType[][] grid) {
        final Maze maze = new Maze(height, width, grid);
        final CellType[][] mazeGrid = maze.grid();

        assertEquals(maze.height(), height);
        assertEquals(maze.width(), width);
        assertEquals(maze.height(), mazeGrid.length);
        assertEquals(maze.width(), mazeGrid[0].length);
        assertEquals(mazeGrid.length, grid.length);
        assertEquals(mazeGrid[0].length, grid[0].length);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                assertEquals(grid[i][j], mazeGrid[i][j]);
            }
        }
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateMazeWithIncorrectHeight")
    public void createMazeWithIncorrectHeight_ShouldThrowIncorrectMazeHeightException(int incorrectHeight) {
        assertThatThrownBy(() -> {
            new Maze(incorrectHeight, DEFAULT_WIDTH, getRandomGrid(DEFAULT_HEIGHT, DEFAULT_WIDTH));
        }).isInstanceOf(IncorrectMazeHeightException.class)
            .hasMessageContaining(INCORRECT_MAZE_HEIGHT_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateMazeWithIncorrectWidth")
    public void createMazeWithIncorrectWidth_ShouldThrowIncorrectMazeWidthException(int incorrectWidth) {
        assertThatThrownBy(() -> {
            new Maze(DEFAULT_HEIGHT, incorrectWidth, getRandomGrid(DEFAULT_HEIGHT, DEFAULT_WIDTH));
        }).isInstanceOf(IncorrectMazeWidthException.class)
            .hasMessageContaining(INCORRECT_MAZE_WIDTH_EXCEPTION_TEXT);
    }

    @Test
    public void createMazeWithNullGrid_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            new Maze(DEFAULT_HEIGHT, DEFAULT_WIDTH, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_MAZE_GRID_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateMazeWithIncorrectHeightInGrid")
    public void createMazeWithIncorrectHeightInGrid_ShouldThrowIncorrectMazeGridException(int height, CellType[][] incorrectGrid) {
        assertThatThrownBy(() -> {
            new Maze(height, DEFAULT_WIDTH, incorrectGrid);
        }).isInstanceOf(IncorrectMazeGridException.class)
            .hasMessageContaining(INCORRECT_MAZE_GRID_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateMazeWithIncorrectWidthInGrid")
    public void createMazeWithIncorrectWidthInGrid_ShouldThrowIncorrectMazeGridException(int width, CellType[][] incorrectGrid) {
        assertThatThrownBy(() -> {
            new Maze(DEFAULT_HEIGHT, width, incorrectGrid);
        }).isInstanceOf(IncorrectMazeGridException.class)
            .hasMessageContaining(INCORRECT_MAZE_GRID_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getGrids")
    public void createMazeWithNullCellType_ShouldThrowIncorrectMazeGridException(CellType[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                final CellType cellType = grid[i][j];

                grid[i][j] = null;

                assertThatThrownBy(() -> {
                    new Maze(grid.length, grid[0].length, grid);
                }).isInstanceOf(IncorrectMazeGridException.class)
                    .hasMessageContaining(INCORRECT_MAZE_GRID_EXCEPTION_TEXT);

                grid[i][j] = cellType;
            }
        }
    }

    @Test
    public void createMazeWithoutEntryExit_ShouldThrowMazeWithIncorrectEntryExitException() {
        final CellType[][] grid = getRandomGrid(MAX_MAZE_HEIGHT, MAX_MAZE_WIDTH);
        final int firstStartIndex = 0;
        final int secondStartIndex = 0;

        grid[0][0] = CellType.WALL;
        grid[0][1] = CellType.WALL;
        tryCreateMazeWithIncorrectEntryExit(grid);

        createMazesWithGridsWithIncorrectEntryExit(grid, firstStartIndex, secondStartIndex);
    }

    @Test
    public void createMazeWithSomeEntriesAndExits_ShouldThrowMazeWithIncorrectEntryExitException() {
        final CellType[][] grid = getRandomGrid(MAX_MAZE_HEIGHT, MAX_MAZE_WIDTH);
        final int firstStartIndex = 2;
        final int secondStartIndex = 1;

        grid[0][0] = CellType.PASSAGE;
        grid[0][1] = CellType.PASSAGE;

        createMazesWithGridsWithIncorrectEntryExit(grid, firstStartIndex, secondStartIndex);
    }

    private void createMazesWithGridsWithIncorrectEntryExit(CellType[][] grid, int firstStartIndex, int secondStartIndex) {
        for (int i = firstStartIndex; i < grid[0].length; i++) {
            grid[0][i] = CellType.PASSAGE;
            tryCreateMazeWithIncorrectEntryExit(grid);
            grid[0][i] = CellType.WALL;
            grid[grid.length - 1][i] = CellType.PASSAGE;
            tryCreateMazeWithIncorrectEntryExit(grid);
            grid[grid.length - 1][i] = CellType.WALL;
        }

        for (int i = secondStartIndex; i < grid.length; i++) {
            grid[i][0] = CellType.PASSAGE;
            tryCreateMazeWithIncorrectEntryExit(grid);
            grid[i][0] = CellType.WALL;
            grid[i][grid[0].length - 1] = CellType.PASSAGE;
            tryCreateMazeWithIncorrectEntryExit(grid);
            grid[i][grid[0].length - 1] = CellType.WALL;
        }
    }

    private void tryCreateMazeWithIncorrectEntryExit(CellType[][] grid) {
        assertThatThrownBy(() -> {
            new Maze(grid.length, grid[0].length, grid);
        }).isInstanceOf(MazeWithIncorrectEntryExitException.class)
            .hasMessageContaining(INCORRECT_ENTRY_EXIT_IN_MAZE_EXCEPTION_TEXT);
    }

    private static List<Object> getArgumentsForCreateMazeWithIncorrectHeightInGrid() {
        return getArgumentsForCreateMazeWithIncorrectSizeInGrid(MIN_MAZE_HEIGHT,
            MAX_MAZE_HEIGHT, i -> new CellType[i + 1][DEFAULT_WIDTH]);
    }

    private static List<Object> getArgumentsForCreateMazeWithIncorrectWidthInGrid() {
        return getArgumentsForCreateMazeWithIncorrectSizeInGrid(MIN_MAZE_WIDTH,
            MAX_MAZE_WIDTH, i -> new CellType[DEFAULT_HEIGHT][i + 1]);
    }


    private static List<Object> getArgumentsForCreateMazeWithIncorrectSizeInGrid(
        int minSize, int maxSize, Function<Integer, CellType[][]> function
    ) {
        final List<Object> result = new ArrayList<>();

        for (int i = minSize; i <= maxSize; i++) {
            result.add(new Object[] {
                i, function.apply(i)
            });
        }

        return result;
    }
}
