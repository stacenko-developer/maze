package backend.academy.mazeTask.generator;

import backend.academy.mazeTask.maze.generator.KruskalMazeGenerator;
import backend.academy.mazeTask.maze.generator.MazeGenerator;

public class KruskalMazeGeneratorTest extends MazeGeneratorTest {

    private final KruskalMazeGenerator kruskalMazeGenerator = new KruskalMazeGenerator();

    @Override
    protected MazeGenerator getMazeGenerator() {
        return kruskalMazeGenerator;
    }
}
