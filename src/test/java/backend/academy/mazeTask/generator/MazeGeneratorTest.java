package backend.academy.mazeTask.generator;

import backend.academy.mazeTask.CommonTest;
import backend.academy.mazeTask.exception.IncorrectMazeHeightException;
import backend.academy.mazeTask.exception.IncorrectMazeWidthException;
import backend.academy.mazeTask.maze.Maze;
import backend.academy.mazeTask.maze.generator.MazeGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.mazeTask.constants.ExceptionTextValues.INCORRECT_MAZE_HEIGHT_EXCEPTION_TEXT;
import static backend.academy.mazeTask.constants.ExceptionTextValues.INCORRECT_MAZE_WIDTH_EXCEPTION_TEXT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class MazeGeneratorTest extends CommonTest {

    protected abstract MazeGenerator getMazeGenerator();

    @ParameterizedTest
    @MethodSource("getArgumentsForGenerateMaze")
    public void generateMaze_ShouldGenerateMaze(int height, int width) {
        Maze maze = getMazeGenerator().generate(height, width);
        assertNotNull(maze);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateMazeWithIncorrectHeight")
    public void generateMazeWithIncorrectHeight_ShouldThrowIncorrectMazeHeightException(int incorrectHeight) {
        assertThatThrownBy(() -> {
            getMazeGenerator().generate(incorrectHeight, DEFAULT_WIDTH);
        }).isInstanceOf(IncorrectMazeHeightException.class)
            .hasMessageContaining(INCORRECT_MAZE_HEIGHT_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateMazeWithIncorrectWidth")
    public void generateMazeWithIncorrectWidth_ShouldThrowIncorrectMazeWidthException(int incorrectWidth) {
        assertThatThrownBy(() -> {
            getMazeGenerator().generate(DEFAULT_HEIGHT, incorrectWidth);
        }).isInstanceOf(IncorrectMazeWidthException.class)
            .hasMessageContaining(INCORRECT_MAZE_WIDTH_EXCEPTION_TEXT);
    }
}
