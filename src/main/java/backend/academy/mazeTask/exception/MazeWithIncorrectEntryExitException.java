package backend.academy.mazeTask.exception;

import static backend.academy.mazeTask.constants.ExceptionTextValues.INCORRECT_ENTRY_EXIT_IN_MAZE_EXCEPTION_TEXT;

public class MazeWithIncorrectEntryExitException extends RuntimeException {
    public MazeWithIncorrectEntryExitException() {
        super(INCORRECT_ENTRY_EXIT_IN_MAZE_EXCEPTION_TEXT);
    }
}
