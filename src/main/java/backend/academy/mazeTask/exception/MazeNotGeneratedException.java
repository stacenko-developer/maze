package backend.academy.mazeTask.exception;

import static backend.academy.mazeTask.constants.ExceptionTextValues.MAZE_NOT_GENERATED_EXCEPTION_TEXT;

public class MazeNotGeneratedException extends RuntimeException {
    public MazeNotGeneratedException() {
        super(MAZE_NOT_GENERATED_EXCEPTION_TEXT);
    }
}
