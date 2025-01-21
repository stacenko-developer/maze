package backend.academy.mazeTask.exception;

import static backend.academy.mazeTask.constants.ExceptionTextValues.INCORRECT_MAZE_GRID_EXCEPTION_TEXT;

public class IncorrectMazeGridException extends RuntimeException {
    public IncorrectMazeGridException() {
        super(INCORRECT_MAZE_GRID_EXCEPTION_TEXT);
    }
}
