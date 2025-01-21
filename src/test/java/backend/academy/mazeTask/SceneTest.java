package backend.academy.mazeTask;

import backend.academy.mazeTask.enums.CellType;
import backend.academy.mazeTask.exception.CoordinateOutOfRangeException;
import backend.academy.mazeTask.maze.Coordinate;
import backend.academy.mazeTask.maze.Maze;
import backend.academy.mazeTask.maze.renderer.Scene;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import static backend.academy.mazeTask.constants.ConstValues.BLUE_COLOR_CODE;
import static backend.academy.mazeTask.constants.ConstValues.DESCRIPTION_SEPARATE;
import static backend.academy.mazeTask.constants.ConstValues.FINISH_PATH_SYMBOL;
import static backend.academy.mazeTask.constants.ConstValues.FINISH_PATH_TEXT;
import static backend.academy.mazeTask.constants.ConstValues.GREEN_COLOR_CODE;
import static backend.academy.mazeTask.constants.ConstValues.MAX_MAZE_HEIGHT;
import static backend.academy.mazeTask.constants.ConstValues.MAX_MAZE_WIDTH;
import static backend.academy.mazeTask.constants.ConstValues.MIN_MAZE_HEIGHT;
import static backend.academy.mazeTask.constants.ConstValues.MIN_MAZE_WIDTH;
import static backend.academy.mazeTask.constants.ConstValues.PATH_NOT_FOUND_TEXT;
import static backend.academy.mazeTask.constants.ConstValues.PATH_SYMBOL;
import static backend.academy.mazeTask.constants.ConstValues.PATH_TEXT;
import static backend.academy.mazeTask.constants.ConstValues.RED_COLOUR_CODE;
import static backend.academy.mazeTask.constants.ConstValues.RESET_COLOUR_CODE;
import static backend.academy.mazeTask.constants.ConstValues.START_PATH_SYMBOL;
import static backend.academy.mazeTask.constants.ConstValues.START_PATH_TEXT;
import static backend.academy.mazeTask.constants.ConstValues.WALL_SYMBOL;
import static backend.academy.mazeTask.constants.ExceptionTextValues.COORDINATE_OUT_OF_RANGE_EXCEPTION_TEXT;
import static backend.academy.mazeTask.constants.ExceptionTextValues.NULL_COORDINATE_EXCEPTION_TEXT;
import static backend.academy.mazeTask.constants.ExceptionTextValues.NULL_MAZE_EXCEPTION_TEXT;
import static backend.academy.mazeTask.constants.ExceptionTextValues.NULL_PATH_EXCEPTION_TEXT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SceneTest extends CommonTest {

    private final Scene scene = new Scene();

    @ParameterizedTest
    @MethodSource("getGrids")
    public void renderMaze_ShouldRenderMaze(CellType[][] grid) {
        renderMaze(grid, null);
    }

    @ParameterizedTest
    @MethodSource("getGridsWithPath")
    public void renderMazeWithPath_ShouldRenderMazeWithPath(CellType[][] grid, List<Coordinate> path) {
        renderMaze(grid, path);
    }

    @Test
    public void renderMazeWithNullMaze_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            scene.renderMaze(null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_MAZE_EXCEPTION_TEXT);
    }

    @Test
    public void renderMazeWithPathWithNullMaze_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            scene.renderMaze(null, getRandomPath(getRandomGrid(MIN_MAZE_HEIGHT, MIN_MAZE_WIDTH)));
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_MAZE_EXCEPTION_TEXT);
    }

    @Test
    public void renderMazeWithPathWithNullPath_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            final CellType[][] randomGrid = getRandomGrid(MIN_MAZE_HEIGHT, MIN_MAZE_WIDTH);
            scene.renderMaze(new Maze(randomGrid.length, randomGrid[0].length, randomGrid), null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_PATH_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getGridsWithPath")
    public void renderMazeWithPathWithNullCoordinate_ShouldThrowNullPointerException(CellType[][] grid, List<Coordinate> path) {
        final int randomIndex = SECURE_RANDOM.nextInt(path.size());
        path.set(randomIndex, null);

        assertThatThrownBy(() -> {
            scene.renderMaze(new Maze(grid.length, grid[0].length, grid), path);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_COORDINATE_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getGridsWithPath")
    public void renderMazeWithPathWithOutOfGridXCoordinates_ShouldThrowCoordinateOutOfRangeException(CellType[][] grid, List<Coordinate> path) {
        final int randomIndex = SECURE_RANDOM.nextInt(path.size());
        final int y = path.get(randomIndex).y();
        final int deviationValue = 100;
        final List<Pair<Integer, Integer>> ranges = List.of(
            new Pair<>(MIN_MAZE_HEIGHT - deviationValue, 0),
            new Pair<>(MAX_MAZE_HEIGHT, MAX_MAZE_HEIGHT + deviationValue)
        );

        for (Pair<Integer, Integer> range : ranges) {
            for (int incorrectX = range.getKey(); incorrectX < range.getValue(); incorrectX++) {
                path.set(randomIndex, new Coordinate(incorrectX, y));
                tryRenderMazeWithOutOfRangeCoordinates(grid, path);
            }
        }
    }

    @ParameterizedTest
    @MethodSource("getGridsWithPath")
    public void renderMazeWithPathWithOutOfGridYCoordinates_ShouldThrowCoordinateOutOfRangeException(CellType[][] grid, List<Coordinate> path) {
        final int randomIndex = SECURE_RANDOM.nextInt(path.size());
        final int x = path.get(randomIndex).x();
        final int deviationValue = 100;
        final List<Pair<Integer, Integer>> ranges = List.of(
            new Pair<>(MIN_MAZE_WIDTH - deviationValue, 0),
            new Pair<>(MAX_MAZE_WIDTH, MAX_MAZE_WIDTH + deviationValue)
        );

        for (Pair<Integer, Integer> range : ranges) {
            for (int incorrectY = range.getKey(); incorrectY < range.getValue(); incorrectY++) {
                path.set(randomIndex, new Coordinate(x, incorrectY));
                tryRenderMazeWithOutOfRangeCoordinates(grid, path);
            }
        }
    }

    @Test
    public void renderMazeDescription_ShouldRenderMazeDescription() {
        final String renderResult = scene.renderMazeDescription();
        final String[] renderResultSplit = renderResult.split("\n");
        final List<CellType> cellTypes = new ArrayList<>(Arrays.stream(CellType.values()).toList());

        cellTypes.remove(CellType.WALL);

        int renderIndex = 0;

        for (CellType cellType : cellTypes) {
            final String cellTypeDescription =
                String.format("%s%s%s", cellType.weight(), DESCRIPTION_SEPARATE, cellType.value());

            assertEquals(renderResultSplit[renderIndex], cellTypeDescription);

            renderIndex++;
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void renderPathDescription_ShouldRenderPathDescription(boolean pathFound) {
        renderPathDescription(pathFound);
    }

    public void tryRenderMazeWithOutOfRangeCoordinates(CellType[][] grid, List<Coordinate> path) {
        assertThatThrownBy(() -> {
            scene.renderMaze(new Maze(grid.length, grid[0].length, grid), path);
        }).isInstanceOf(CoordinateOutOfRangeException.class)
            .hasMessageContaining(COORDINATE_OUT_OF_RANGE_EXCEPTION_TEXT);
    }

    private void renderMaze(CellType[][] grid, List<Coordinate> path) {
        final Maze maze = new Maze(grid.length, grid[0].length, grid);
        final String renderResult = path != null
            ? scene.renderMaze(maze, path)
            : scene.renderMaze(maze);
        final String[] renderResultSplit = renderResult.split("\n");

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0, index = 0; y < grid[0].length; y++, index++) {
                if (renderResultSplit[x].charAt(index) == WALL_SYMBOL) {
                    assertEquals(grid[x][y], CellType.WALL);
                } else {
                    final int finalX = x;
                    final int finalY = y;

                    Coordinate coordinate = null;

                    if (path != null) {
                        coordinate = path
                            .stream()
                            .filter(coord -> coord.x() == finalX && coord.y() == finalY)
                            .findFirst()
                            .orElse(null);
                    }

                    if (coordinate != null) {
                        String result;

                        if (coordinate.equals(path.getFirst())) {
                            result = RED_COLOUR_CODE + START_PATH_SYMBOL + RESET_COLOUR_CODE;
                        } else if (coordinate.equals(path.getLast())) {
                            result = GREEN_COLOR_CODE + FINISH_PATH_SYMBOL + RESET_COLOUR_CODE;
                        } else {
                            result = BLUE_COLOR_CODE + PATH_SYMBOL + RESET_COLOUR_CODE;
                        }

                        final String str = renderResultSplit[x].substring(index, index + result.length());

                        assertEquals(str, result);
                        index += result.length() - 1;
                    } else {
                        assertEquals((char) grid[x][y].weight() + '0', renderResultSplit[x].charAt(index));
                    }
                }
            }
        }
    }

    private void renderPathDescription(boolean isPathFound) {
        final String renderResult = scene.renderPathDescription(isPathFound);
        final String[] renderResultSplit = renderResult.split("\n");

        int renderIndex = 0;

        if (!isPathFound) {
            assertEquals(renderResultSplit[renderIndex], PATH_NOT_FOUND_TEXT);
        } else {
            final List<String> pathDescriptions = List.of(
                String.format("%s%s%s",
                    PATH_SYMBOL, DESCRIPTION_SEPARATE, PATH_TEXT),
                String.format("%s%s%s",
                    START_PATH_SYMBOL, DESCRIPTION_SEPARATE, START_PATH_TEXT),
                String.format("%s%s%s",
                    FINISH_PATH_SYMBOL, DESCRIPTION_SEPARATE, FINISH_PATH_TEXT)
            );

            for (String pathDescription : pathDescriptions) {
                assertEquals(renderResultSplit[renderIndex], pathDescription);
                renderIndex++;
            }

            renderIndex--;
        }

        assertEquals(renderIndex, renderResultSplit.length - 1);
    }
}
