package backend.academy.mazeTask.exception;

import static backend.academy.mazeTask.constants.ExceptionTextValues.INCORRECT_INPUT_EXCEPTION_TEXT;

public class IncorrectInputException extends RuntimeException {
    public IncorrectInputException() {
        super(INCORRECT_INPUT_EXCEPTION_TEXT);
    }
}
