package backend.academy.mazeTask.maze.renderer;

import backend.academy.mazeTask.enums.CellType;
import backend.academy.mazeTask.exception.CoordinateOutOfRangeException;
import backend.academy.mazeTask.maze.Coordinate;
import backend.academy.mazeTask.maze.Maze;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static backend.academy.mazeTask.constants.ConstValues.BLUE_COLOR_CODE;
import static backend.academy.mazeTask.constants.ConstValues.DESCRIPTION_SEPARATE;
import static backend.academy.mazeTask.constants.ConstValues.FINISH_PATH_SYMBOL;
import static backend.academy.mazeTask.constants.ConstValues.FINISH_PATH_TEXT;
import static backend.academy.mazeTask.constants.ConstValues.GREEN_COLOR_CODE;
import static backend.academy.mazeTask.constants.ConstValues.PATH_NOT_FOUND_TEXT;
import static backend.academy.mazeTask.constants.ConstValues.PATH_SYMBOL;
import static backend.academy.mazeTask.constants.ConstValues.PATH_TEXT;
import static backend.academy.mazeTask.constants.ConstValues.RED_COLOUR_CODE;
import static backend.academy.mazeTask.constants.ConstValues.RESET_COLOUR_CODE;
import static backend.academy.mazeTask.constants.ConstValues.START_PATH_SYMBOL;
import static backend.academy.mazeTask.constants.ConstValues.START_PATH_TEXT;
import static backend.academy.mazeTask.constants.ConstValues.WALL_SYMBOL;
import static backend.academy.mazeTask.constants.ExceptionTextValues.NULL_COORDINATE_EXCEPTION_TEXT;
import static backend.academy.mazeTask.constants.ExceptionTextValues.NULL_MAZE_EXCEPTION_TEXT;
import static backend.academy.mazeTask.constants.ExceptionTextValues.NULL_PATH_EXCEPTION_TEXT;

public class Scene {

    /**
     * Визуализирует лабиринт без указания пути.
     *
     * @param maze лабиринт, который нужно отобразить
     *
     * @return строковое представление лабиринта
     *
     * @throws NullPointerException если лабиринт равен null
     */
    public String renderMaze(Maze maze) {
        validateMaze(maze);

        return render(maze, null);
    }

    /**
     * Визуализирует лабиринт с указанием пути.
     *
     * @param maze лабиринт, который нужно отобразить
     * @param path список координат, представляющий путь в лабиринте
     *
     * @return строковое представление лабиринта с отображением пути
     *
     * @throws NullPointerException если лабиринт или путь равны null
     * @throws CoordinateOutOfRangeException если координаты вышлы за пределы лабиринта
     */
    public String renderMaze(Maze maze, List<Coordinate> path) {
        validateMaze(maze);
        validatePath(maze, path);

        return render(maze, path);
    }

    /**
     * Визуализирует описание к лабиринту
     *
     * @return строковое представление описания лабиринта
     *
     */
    public String renderMazeDescription() {
        final StringBuilder result = new StringBuilder();
        final List<CellType> cellTypes = new ArrayList<>(Arrays.stream(CellType.values()).toList());

        cellTypes.remove(CellType.WALL);

        for (final CellType cellType : cellTypes) {
            result.append(cellType.weight())
                .append(DESCRIPTION_SEPARATE)
                .append(cellType.value())
                .append('\n');
        }

        return result.deleteCharAt(result.length() - 1).toString();
    }

    /**
     * Визуализирует описание результата поиска пути в лабиринте
     *
     * @param isPathFound найден ли путь из точки a в точку b
     *
     * @return строковое представление описания пути лабиринта
     *
     */
    public String renderPathDescription(boolean isPathFound) {
        final StringBuilder result = new StringBuilder();

        if (!isPathFound) {
            result.append(PATH_NOT_FOUND_TEXT);
        } else {
            result.append(PATH_SYMBOL)
                .append(DESCRIPTION_SEPARATE)
                .append(PATH_TEXT)
                .append('\n')
                .append(START_PATH_SYMBOL)
                .append(DESCRIPTION_SEPARATE)
                .append(START_PATH_TEXT)
                .append('\n')
                .append(FINISH_PATH_SYMBOL)
                .append(DESCRIPTION_SEPARATE)
                .append(FINISH_PATH_TEXT);
        }

        return result.toString();
    }

    private void validateMaze(Maze maze) {
        if (maze == null) {
            throw new NullPointerException(NULL_MAZE_EXCEPTION_TEXT);
        }
    }

    private void validatePath(Maze maze, List<Coordinate> path) {
        if (path == null) {
            throw new NullPointerException(NULL_PATH_EXCEPTION_TEXT);
        }

        for (Coordinate coordinate : path) {
            if (coordinate == null) {
                throw new NullPointerException(NULL_COORDINATE_EXCEPTION_TEXT);
            }

            if (isInvalidRange(coordinate, maze)) {
                throw new CoordinateOutOfRangeException();
            }
        }
    }

    private boolean isInvalidRange(Coordinate coordinate, Maze maze) {
        return coordinate.x() < 0 || coordinate.x() >= maze.height()
            || coordinate.y() < 0 || coordinate.y() >= maze.width();
    }

    private String render(Maze maze, List<Coordinate> path) {
        final StringBuilder result = new StringBuilder();
        final CellType[][] grid = maze.grid();

        for (int x = 0; x < maze.height(); x++) {
            for (int y = 0; y < maze.width(); y++) {
                if (grid[x][y] == CellType.WALL) {
                    result.append(WALL_SYMBOL);
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
                        if (coordinate.equals(path.getFirst())) {
                            result.append(RED_COLOUR_CODE + START_PATH_SYMBOL);
                        } else if (coordinate.equals(path.getLast())) {
                            result.append(GREEN_COLOR_CODE + FINISH_PATH_SYMBOL);
                        } else {
                            result.append(BLUE_COLOR_CODE + PATH_SYMBOL);
                        }

                        result.append(RESET_COLOUR_CODE);
                    } else {
                        result.append((char) (grid[x][y].weight() + '0'));
                    }
                }
            }
            result.append('\n');
        }

        return result.toString();
    }
}
