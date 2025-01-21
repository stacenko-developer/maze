package backend.academy.mazeTask.exception;

import static backend.academy.mazeTask.constants.ExceptionTextValues.COORDINATE_OUT_OF_RANGE_EXCEPTION_TEXT;

public class CoordinateOutOfRangeException extends RuntimeException {
    public CoordinateOutOfRangeException() {
        super(COORDINATE_OUT_OF_RANGE_EXCEPTION_TEXT);
    }
}
