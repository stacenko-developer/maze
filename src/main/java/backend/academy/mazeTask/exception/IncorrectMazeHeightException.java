package backend.academy.mazeTask.exception;

import static backend.academy.mazeTask.constants.ExceptionTextValues.INCORRECT_MAZE_HEIGHT_EXCEPTION_TEXT;

public class IncorrectMazeHeightException extends RuntimeException {
    public IncorrectMazeHeightException() {
        super(INCORRECT_MAZE_HEIGHT_EXCEPTION_TEXT);
    }
}
