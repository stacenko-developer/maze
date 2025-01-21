package backend.academy.mazeTask.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ConstValues {

    public static final int MIN_MAZE_HEIGHT = 5;
    public static final int MAX_MAZE_HEIGHT = 50;
    public static final int MIN_MAZE_WIDTH = 5;
    public static final int MAX_MAZE_WIDTH = 50;

    public static final char WALL_SYMBOL = '#';
    public static final char PATH_SYMBOL = 'R';
    public static final char START_PATH_SYMBOL = 'S';
    public static final char FINISH_PATH_SYMBOL = 'F';
    public static final String PATH_NOT_FOUND_TEXT = "Путь не найден";
    public static final String START_PATH_TEXT = "Начало пути";
    public static final String FINISH_PATH_TEXT = "Конец пути";
    public static final String PATH_TEXT = "Пройденный путь";
    public static final String DESCRIPTION_SEPARATE = " - ";

    public static final String RESET_COLOUR_CODE = "\u001B[0m";
    public static final String RED_COLOUR_CODE = "\u001B[31m";
    public static final String BLUE_COLOR_CODE = "\u001B[34m";
    public static final String GREEN_COLOR_CODE = "\u001B[32m";

}
