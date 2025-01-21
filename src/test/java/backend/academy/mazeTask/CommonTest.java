package backend.academy.mazeTask;

import backend.academy.mazeTask.enums.CellType;
import backend.academy.mazeTask.maze.Coordinate;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import backend.academy.mazeTask.maze.Maze;
import org.apache.commons.math3.util.Pair;
import static backend.academy.mazeTask.constants.ConstValues.MAX_MAZE_HEIGHT;
import static backend.academy.mazeTask.constants.ConstValues.MAX_MAZE_WIDTH;
import static backend.academy.mazeTask.constants.ConstValues.MIN_MAZE_HEIGHT;
import static backend.academy.mazeTask.constants.ConstValues.MIN_MAZE_WIDTH;

public class CommonTest {

    protected static final int DEFAULT_HEIGHT = 10;
    protected static final int DEFAULT_WIDTH = 10;

    protected static final int DEFAULT_X = 1;
    protected static final int DEFAULT_Y = 1;

    protected static final SecureRandom SECURE_RANDOM = new SecureRandom();

    protected static CellType[][] getRandomGrid(int height, int width) {
        final CellType[][] grid = new CellType[height][width];

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (isFrontier(grid, x, y)) {
                    grid[x][y] = CellType.WALL;
                } else {
                    grid[x][y] = getRandomCellType();
                }
            }
        }

        grid[0][0] = CellType.PASSAGE;
        grid[0][1] = CellType.PASSAGE;

        return grid;
    }

    protected static CellType getRandomCellType() {
        return CellType.values()[SECURE_RANDOM.nextInt(CellType.values().length - 1)];
    }

    protected static boolean isFrontier(CellType[][] grid, int x, int y) {
        return x == 0 || x == grid.length - 1 || y == 0 || y == grid[0].length - 1;
    }

    protected static List<Object[]> getArgumentsForCreateMaze() {
        final List<Object[]> testValues = new ArrayList<>();

        for (int a = MIN_MAZE_HEIGHT; a <= MAX_MAZE_HEIGHT; a++) {
            for (int b = MIN_MAZE_WIDTH; b <= MAX_MAZE_WIDTH; b++) {
                testValues.add(new Object[] {
                    a,
                    b,
                    getRandomGrid(a, b)
                });
            }
        }

        return testValues;
    }

    protected static List<CellType[][]> getGrids() {
        final int gridIndex = 2;

        return getArgumentsForCreateMaze()
            .stream()
            .map(obj -> (CellType[][]) obj[gridIndex])
            .toList();
    }

    protected static List<Object[]> getGridsWithPath() {
        final int gridIndex = 2;

        return getArgumentsForCreateMaze()
            .stream()
            .map(obj -> {
                final CellType[][] grid = (CellType[][]) obj[gridIndex];
                return new Object[] {grid,getRandomPath(grid) };
            })
            .toList();
    }

    protected static List<Coordinate> getRandomPath(CellType[][] grid) {
        final List<Coordinate> result = new ArrayList<>();
        final int maxCoordinateValue = 10;

        for (int i = 0; i < maxCoordinateValue; i++) {
            final int randomX = SECURE_RANDOM.nextInt(grid.length - 1);
            final int randomY = SECURE_RANDOM.nextInt(grid[0].length - 1);

            result.add(new Coordinate(randomX, randomY));
        }

        return result;
    }

    protected static List<Object[]> getArgumentsForGenerateMaze() {
        return getArgumentsForCreateMaze()
            .stream()
            .map(obj -> new Object[]{obj[0], obj[1]})
            .toList();
    }

    protected static List<Integer> getArgumentsForCreateMazeWithIncorrectHeight() {
        final int minIncorrectHeight = -100;
        final int maxIncorrectHeight = 100;
        final List<Pair<Integer, Integer>> ranges = List.of(
            new Pair<>(minIncorrectHeight, MIN_MAZE_HEIGHT),
            new Pair<>(MAX_MAZE_HEIGHT + 1, maxIncorrectHeight)
        );

        return getArgumentsForCreateMazeWithIncorrectSize(ranges);
    }

    protected static List<Integer> getArgumentsForCreateMazeWithIncorrectWidth() {
        final int minIncorrectHeight = -100;
        final int maxIncorrectHeight = 100;
        final List<Pair<Integer, Integer>> ranges = List.of(
            new Pair<>(minIncorrectHeight, MIN_MAZE_WIDTH),
            new Pair<>(MAX_MAZE_WIDTH + 1, maxIncorrectHeight)
        );

        return getArgumentsForCreateMazeWithIncorrectSize(ranges);
    }

    protected static List<Integer> getArgumentsForCreateMazeWithIncorrectSize(List<Pair<Integer, Integer>> ranges) {
        final List<Integer> result = new ArrayList<>();

        for (Pair<Integer, Integer> range : ranges) {
            for (int i = range.getKey(); i < range.getValue(); i++) {
                result.add(i);
            }
        }

        return result;
    }

    protected Maze getRandomMaze(int height, int width) {
        return new Maze(height, width, getRandomGrid(height, width));
    }

    protected static List<Object[]> getArgumentsForFindShortestWay() {
        final CellType[][] grid = {
            {CellType.WALL, CellType.PASSAGE, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL},
            {CellType.WALL, CellType.PASSAGE, CellType.PASSAGE, CellType.WALL, CellType.PASSAGE, CellType.PASSAGE, CellType.WALL, CellType.PASSAGE, CellType.PASSAGE, CellType.WALL},
            {CellType.WALL, CellType.WALL, CellType.PASSAGE, CellType.WALL, CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE_WITH_GOOD_COVERAGE, CellType.WALL, CellType.WALL, CellType.WALL},
            {CellType.WALL, CellType.WALL, CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE, CellType.WALL, CellType.PASSAGE_WITH_GOOD_COVERAGE, CellType.PASSAGE_WITH_GOOD_COVERAGE, CellType.WALL, CellType.WALL},
            {CellType.WALL, CellType.WALL, CellType.PASSAGE, CellType.WALL, CellType.PASSAGE_WITH_SAND, CellType.WALL, CellType.PASSAGE, CellType.SWAMP, CellType.WALL, CellType.WALL},
            {CellType.WALL, CellType.WALL, CellType.PASSAGE, CellType.SWAMP, CellType.PASSAGE_WITH_GOOD_COVERAGE, CellType.WALL, CellType.PASSAGE_WITH_SAND, CellType.WALL, CellType.WALL, CellType.WALL},
            {CellType.WALL, CellType.WALL, CellType.PASSAGE, CellType.WALL, CellType.PASSAGE_WITH_SAND, CellType.WALL, CellType.PASSAGE_WITH_GOOD_COVERAGE, CellType.PASSAGE_WITH_GOOD_COVERAGE, CellType.PASSAGE_WITH_GOOD_COVERAGE, CellType.WALL},
            {CellType.WALL, CellType.PASSAGE_WITH_GOOD_COVERAGE, CellType.PASSAGE, CellType.PASSAGE_WITH_GOOD_COVERAGE, CellType.PASSAGE_WITH_GOOD_COVERAGE, CellType.WALL, CellType.PASSAGE_WITH_GOOD_COVERAGE, CellType.WALL, CellType.WALL, CellType.WALL},
            {CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL},
            {CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL}
        };
        final List<Coordinate> starts = List.of(
            new Coordinate(1, 8),
            new Coordinate(0, 0),
            new Coordinate(1, 1),
            new Coordinate(1, 1),
            new Coordinate(0, 1),
            new Coordinate(5, 3),
            new Coordinate(8, 0),
            new Coordinate(7, 3),
            new Coordinate(2, 4),
            new Coordinate(6, 8),
            new Coordinate(7, 4)
        );
        final List<Coordinate> finishes = List.of(
            new Coordinate(8, 0),
            new Coordinate(0, 1),
            new Coordinate(9, 9),
            new Coordinate(1, 1),
            new Coordinate(8, 0),
            new Coordinate(7, 3),
            new Coordinate(7, 2),
            new Coordinate(3, 3),
            new Coordinate(4, 7),
            new Coordinate(7, 6),
            new Coordinate(5, 4)
        );
        final List<List<Coordinate>> correctPaths = List.of(
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            List.of(
                new Coordinate(1, 1)
            ),
            List.of(
                new Coordinate(0, 1),
                new Coordinate(1, 1),
                new Coordinate(1, 2),
                new Coordinate(2, 2),
                new Coordinate(3, 2),
                new Coordinate(4, 2),
                new Coordinate(5, 2),
                new Coordinate(6, 2),
                new Coordinate(7, 2),
                new Coordinate(7, 1),
                new Coordinate(8, 1),
                new Coordinate(8, 0)
            ),
            List.of(
                new Coordinate(5, 3),
                new Coordinate(5, 4),
                new Coordinate(6, 4),
                new Coordinate(7, 4),
                new Coordinate(7, 3)
            ),
            List.of(
                new Coordinate(8, 0),
                new Coordinate(8, 1),
                new Coordinate(7, 1),
                new Coordinate(7, 2)
            ),
            List.of(
                new Coordinate(7, 3),
                new Coordinate(7, 4),
                new Coordinate(6, 4),
                new Coordinate(5, 4),
                new Coordinate(4, 4),
                new Coordinate(3, 4),
                new Coordinate(3, 3)
            ),
            List.of(
                new Coordinate(2, 4),
                new Coordinate(2, 5),
                new Coordinate(2, 6),
                new Coordinate(3, 6),
                new Coordinate(3, 7),
                new Coordinate(4, 7)
            ),
            List.of(
                new Coordinate(6, 8),
                new Coordinate(6, 7),
                new Coordinate(6, 6),
                new Coordinate(7, 6)
            ),
            List.of(
                new Coordinate(7, 4),
                new Coordinate(6, 4),
                new Coordinate(5, 4)
            )
        );

        final List<Object[]> result = new ArrayList<>();

        for (int i = 0; i < starts.size(); i++) {
            result.add(new Object[]{
                grid,
                starts.get(i),
                finishes.get(i),
                correctPaths.get(i)
            });
        }

        return result;
    }
}
