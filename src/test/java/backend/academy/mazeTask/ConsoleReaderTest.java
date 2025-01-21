package backend.academy.mazeTask;

import backend.academy.mazeTask.console.ConsoleReader;
import backend.academy.mazeTask.exception.IncorrectInputException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import static backend.academy.mazeTask.constants.ExceptionTextValues.INCORRECT_INPUT_EXCEPTION_TEXT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ConsoleReaderTest {

    @ParameterizedTest
    @MethodSource("getArgumentsForReadPositiveInteger")
    public void readPositiveInteger_ShouldReturnPositiveIntegerFrom(String input, int minValue, int maxValue, int correctResult) {
        final int number = ConsoleReader.convertToPositiveInteger(input, minValue, maxValue);

        assertEquals(number, correctResult);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForReadPositiveIntegerByIncorrectInput")
    public void readPositiveNumberByIncorrectInput_ShouldThrowIncorrectInputException(String input) {
        final int defaultMinValue = 0;
        final int defaultMaxValue = 0;

        assertThatThrownBy(() -> {
            ConsoleReader.convertToPositiveInteger(input, defaultMinValue, defaultMaxValue);
        }).isInstanceOf(IncorrectInputException.class)
            .hasMessageContaining(INCORRECT_INPUT_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForReadPositiveIntegerByIncorrectRange")
    public void readPositiveNumberByIncorrectRange_ShouldThrowIncorrectInputException(String input, int minValue, int maxValue) {
        assertThatThrownBy(() -> {
            ConsoleReader.convertToPositiveInteger(input, minValue, maxValue);
        }).isInstanceOf(IncorrectInputException.class)
            .hasMessageContaining(INCORRECT_INPUT_EXCEPTION_TEXT);
    }

    private static List<Object[]> getArgumentsForReadPositiveInteger() {
        final List<Object[]> testValues = new ArrayList<>();
        final int numbersCount = 50;

        for (int a = 0; a < numbersCount; a++) {
            for (int b = 0; b < numbersCount; b++) {
                testValues.add(new Object[] {
                    String.valueOf(a),
                    a - b,
                    a + b,
                    a
                });
            }
        }

        return testValues;
    }

    private static List<String> getArgumentsForReadPositiveIntegerByIncorrectInput() {
        final List<String> testValues = new ArrayList<>();
        final List<Pair<Integer, Integer>> incorrectSymbolsNumbersRanges = List.of(
            new Pair<>(32, 48),
            new Pair<>(58, 127)
        );

        for (Pair<Integer, Integer> range : incorrectSymbolsNumbersRanges) {
            for (int i = range.getKey(); i < range.getValue(); i++) {
                testValues.add(String.valueOf((char) i));
            }
        }

        return testValues;
    }

    private static List<Object[]> getArgumentsForReadPositiveIntegerByIncorrectRange() {
        final List<Object[]> testValues = new ArrayList<>();
        final int numbersCount = 50;

        for (int i = 0; i < numbersCount; i++) {
            for (int b = 0; b < i; b++) {
                testValues.add(new Object[] {
                    String.valueOf(i), b, i - 1
                });
            }
            for (int b = i + 1; b < numbersCount; b++) {
                testValues.add(new Object[] {
                    String.valueOf(i), b, numbersCount
                });
            }
        }

        return testValues;
    }
}
