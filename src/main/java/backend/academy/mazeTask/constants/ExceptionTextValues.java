package backend.academy.mazeTask.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionTextValues {
    public static final String INCORRECT_INPUT_EXCEPTION_TEXT = "Введено некорректное значение";
    public static final String INCORRECT_MAZE_HEIGHT_EXCEPTION_TEXT = "Высота лабиринта вышла за допустимые пределы";
    public static final String INCORRECT_MAZE_WIDTH_EXCEPTION_TEXT = "Ширина лабиринта вышла за допустимые пределы";
    public static final String INCORRECT_MAZE_GRID_EXCEPTION_TEXT = "Некорректный формат массива ячеек лабиринта";
    public static final String INCORRECT_ENTRY_EXIT_IN_MAZE_EXCEPTION_TEXT = "В лабиринте должен быть 1 вход и 1 выход";

    public static final String NULL_MAZE_EXCEPTION_TEXT = "Лабиринт не должен быть null";
    public static final String NULL_COORDINATE_EXCEPTION_TEXT = "Координата не должна быть null";
    public static final String NULL_PATH_EXCEPTION_TEXT = "Путь не должен быть null";
    public static final String NULL_MAZE_GRID_EXCEPTION_TEXT = "Массив ячеек лабиринта не должен быть null";

    public static final String COORDINATE_OUT_OF_RANGE_EXCEPTION_TEXT = "Начальная координата вышла за пределы";
    public static final String UNKNOWN_ERROR_EXCEPTION_TEXT = "Неизвестная ошибка";
    public static final String MAZE_NOT_GENERATED_EXCEPTION_TEXT = "Лабиринт еще не сгенерирован";

    public static final String MAZE_NOT_GENERATED_DECISION = "Чтобы сгенерировать лабиринт, необходимо выбрать "
        + "соответствующий пункт меню, указав высоту и ширину";
}
