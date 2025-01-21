package backend.academy.mazeTask.solver;

import backend.academy.mazeTask.maze.solver.DfsMazeSolver;
import backend.academy.mazeTask.maze.solver.MazeSolver;

public class DfsMazeSolverTest extends MazeSolverTest {

    private final DfsMazeSolver dfsMazeSolver = new DfsMazeSolver();

    @Override
    protected MazeSolver getMazeSolver() {
        return dfsMazeSolver;
    }
}
