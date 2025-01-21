package backend.academy.mazeTask.maze.generator;

import backend.academy.mazeTask.enums.CellType;
import backend.academy.mazeTask.exception.IncorrectMazeHeightException;
import backend.academy.mazeTask.exception.IncorrectMazeWidthException;
import backend.academy.mazeTask.maze.Maze;
import java.security.SecureRandom;
import java.util.Arrays;
import static backend.academy.mazeTask.constants.ConstValues.MAX_MAZE_HEIGHT;
import static backend.academy.mazeTask.constants.ConstValues.MAX_MAZE_WIDTH;
import static backend.academy.mazeTask.constants.ConstValues.MIN_MAZE_HEIGHT;
import static backend.academy.mazeTask.constants.ConstValues.MIN_MAZE_WIDTH;

public abstract class MazeGenerator {

    protected final SecureRandom secureRandom = new SecureRandom();
    protected int width;
    protected int height;

    protected void validateData(int height, int width) {
        if (height < MIN_MAZE_HEIGHT || height > MAX_MAZE_HEIGHT) {
            throw new IncorrectMazeHeightException();
        }

        if (width < MIN_MAZE_WIDTH || width > MAX_MAZE_WIDTH) {
            throw new IncorrectMazeWidthException();
        }
    }

    protected void setCellType(CellType[][] grid, int x, int y, CellType value) {
        if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
            grid[x][y] = value;
        }
    }

    protected CellType getRandomPassageView() {
        return Arrays.stream(CellType.values())
            .filter(cellType -> cellType != CellType.WALL)
            .skip(secureRandom.nextInt(CellType.values().length - 1))
            .findFirst()
            .orElse(CellType.PASSAGE);
    }

    protected void setEntryExit(CellType[][] grid) {
        final int minValue = 1;

        int y;
        int x;

        do {
            y = secureRandom.nextInt(minValue, width - 2);
        } while (getElementByIndex(grid, minValue, y) == CellType.WALL);

        grid[0][y] = CellType.PASSAGE;

        do {
            x = secureRandom.nextInt(minValue, grid.length - 2);
        } while (getElementByIndex(grid, x, minValue) == CellType.WALL);

        grid[x][0] = CellType.PASSAGE;
    }

    private CellType getElementByIndex(CellType[][] grid, int x, int y) {
        if (x >= 0 && y >= 0 && x < grid.length && y < grid[0].length) {
            return grid[x][y];
        }

        return null;
    }

    public abstract Maze generate(int height, int width);
}
