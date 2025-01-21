package backend.academy.mazeTask.generator;

import backend.academy.mazeTask.maze.generator.MazeGenerator;
import backend.academy.mazeTask.maze.generator.PrimMazeGenerator;

public class PrimMazeGeneratorTest extends MazeGeneratorTest {

    private final PrimMazeGenerator primMazeGenerator = new PrimMazeGenerator();

    @Override
    protected MazeGenerator getMazeGenerator() {
        return primMazeGenerator;
    }
}
