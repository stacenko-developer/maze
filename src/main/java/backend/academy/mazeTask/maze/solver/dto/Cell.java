package backend.academy.mazeTask.maze.solver.dto;

import backend.academy.mazeTask.enums.CellType;
import backend.academy.mazeTask.maze.Coordinate;

public record Cell(Coordinate coordinate, CellType cellType) {
}
