package backend.academy.mazeTask.maze;

import backend.academy.mazeTask.enums.CellType;
import backend.academy.mazeTask.exception.IncorrectMazeGridException;
import backend.academy.mazeTask.exception.IncorrectMazeHeightException;
import backend.academy.mazeTask.exception.IncorrectMazeWidthException;
import backend.academy.mazeTask.exception.MazeWithIncorrectEntryExitException;
import static backend.academy.mazeTask.constants.ConstValues.MAX_MAZE_HEIGHT;
import static backend.academy.mazeTask.constants.ConstValues.MAX_MAZE_WIDTH;
import static backend.academy.mazeTask.constants.ConstValues.MIN_MAZE_HEIGHT;
import static backend.academy.mazeTask.constants.ConstValues.MIN_MAZE_WIDTH;
import static backend.academy.mazeTask.constants.ExceptionTextValues.NULL_MAZE_GRID_EXCEPTION_TEXT;

/**
 * Представляет лабиринт с заданной высотой, шириной и сеткой ячеек типа {@link CellType}.
 *
 * @param height высота лабиринта, должна быть в пределах от MIN_MAZE_HEIGHT до MAX_MAZE_HEIGHT
 * @param width ширина лабиринта, должна быть в пределах от MIN_MAZE_WIDTH до MAX_MAZE_WIDTH
 * @param grid двумерный массив {@link CellType}, представляющий сетку лабиринта
 *
 */
public record Maze(int height, int width, CellType[][] grid) {
    public Maze {
        if (height < MIN_MAZE_HEIGHT || height > MAX_MAZE_HEIGHT) {
            throw new IncorrectMazeHeightException();
        }

        if (width < MIN_MAZE_WIDTH || width > MAX_MAZE_WIDTH) {
            throw new IncorrectMazeWidthException();
        }

        if (grid == null) {
            throw new NullPointerException(NULL_MAZE_GRID_EXCEPTION_TEXT);
        }

        if (height != grid.length || width != grid[0].length) {
            throw new IncorrectMazeGridException();
        }

        validateCellTypes(grid, height, width);
        validatePassageCount(grid, height, width);
    }

    private void validateCellTypes(CellType[][] grid, int height, int width) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == null) {
                    throw new IncorrectMazeGridException();
                }
            }
        }
    }

    private void validatePassageCount(CellType[][] grid, int height, int width) {
        final int correctPassageCount = 2;
        int passageCount = 0;

        for (int i = 0; i < width; i++) {
            if (getElementByIndex(grid, 0, i) != CellType.WALL) {
                passageCount++;
            }
            if (getElementByIndex(grid, height - 1, i) != CellType.WALL) {
                passageCount++;
            }
        }

        for (int i = 1; i < height - 1; i++) {
            if (getElementByIndex(grid, i, 0) != CellType.WALL) {
                passageCount++;
            }
            if (getElementByIndex(grid, i, width - 1) != CellType.WALL) {
                passageCount++;
            }
        }

        if (passageCount != correctPassageCount) {
            throw new MazeWithIncorrectEntryExitException();
        }
    }

    private CellType getElementByIndex(CellType[][] grid, int x, int y) {
        if (x >= 0 && y >= 0 && x < grid.length && y < grid[0].length) {
            return grid[x][y];
        }

        return null;
    }
}
