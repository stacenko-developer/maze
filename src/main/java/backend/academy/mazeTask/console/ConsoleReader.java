package backend.academy.mazeTask.console;

import backend.academy.mazeTask.exception.IncorrectInputException;
import java.nio.charset.Charset;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

/**
 * Предназначен для чтения и валидации ввода с консоли.
 */
@UtilityClass
public class ConsoleReader {

    private static final String DIGITS_ONLY_REGEX = "^[0-9]+$";
    private static final Scanner SCANNER = new Scanner(System.in, Charset.defaultCharset());

    /**
     * Читает положительное целое число из ввода с консоли и проверяет его в пределах заданного диапазона.
     *
     * @param minValue минимально допустимое значение (включительно)
     * @param maxValue максимально допустимое значение (включительно)
     *
     * @return корректное целое число в пределах от minValue до maxValue
     */
    public static int readPositiveInteger(int minValue, int maxValue) {
        while (true) {
            try {
                return convertToPositiveInteger(SCANNER.nextLine(), minValue, maxValue);
            } catch (IncorrectInputException ex) {
                final String incorrectNumberDecisionFormat = "Необходимо ввести число в диапазоне [%d, %d]";

                ConsolePrinter.printErrorText(ex.getMessage(),
                    String.format(incorrectNumberDecisionFormat, minValue, maxValue));
            }
        }
    }

    /**
     * Конвертирует строку в число.
     *
     * @param minValue минимально допустимое значение (включительно)
     * @param maxValue максимально допустимое значение (включительно)
     *
     * @return корректное целое число в пределах от minValue до maxValue
     *
     * @throws IncorrectInputException если ввод некорректный
     */
    public static int convertToPositiveInteger(String input, int minValue, int maxValue) {
        if (isInvalidNumber(input, minValue, maxValue)) {
            throw new IncorrectInputException();
        }

        return Integer.parseInt(input);
    }

    private static boolean isInvalidNumber(String input, int minValue, int maxValue) {
        try {
            return !input.matches(DIGITS_ONLY_REGEX)
                || Integer.parseInt(input) < minValue || Integer.parseInt(input) > maxValue;
        } catch (Exception ex) {
            return true;
        }
    }
}
