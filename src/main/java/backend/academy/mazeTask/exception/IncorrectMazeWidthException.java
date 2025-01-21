package backend.academy.mazeTask.exception;

import static backend.academy.mazeTask.constants.ExceptionTextValues.INCORRECT_MAZE_WIDTH_EXCEPTION_TEXT;

public class IncorrectMazeWidthException extends RuntimeException {
    public IncorrectMazeWidthException() {
        super(INCORRECT_MAZE_WIDTH_EXCEPTION_TEXT);
    }
}
