package backend.academy.mazeTask.solver;

import backend.academy.mazeTask.maze.solver.BfsMazeSolver;
import backend.academy.mazeTask.maze.solver.MazeSolver;

public class BfsMazeSolverTest extends MazeSolverTest {

    private final BfsMazeSolver bfsMazeSolver = new BfsMazeSolver();

    @Override
    protected MazeSolver getMazeSolver() {
        return bfsMazeSolver;
    }
}
