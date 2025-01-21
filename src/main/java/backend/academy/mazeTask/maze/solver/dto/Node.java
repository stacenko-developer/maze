package backend.academy.mazeTask.maze.solver.dto;

import java.util.List;

public record Node(Cell cell, List<Cell> path) {
}
