package backend.academy.mazeTask.enums;

import lombok.Getter;

@Getter
public enum CellType {
    WALL("Забор", Integer.MAX_VALUE),
    PASSAGE_WITH_GOOD_COVERAGE("Проход с хорошим покрытием", 1),
    PASSAGE("Обычный проход", 2),
    PASSAGE_WITH_SAND("Проход с песком", 3),
    SWAMP("Болото", 4);

    private final String value;
    private final int weight;

    CellType(String value, int weight) {
        this.value = value;
        this.weight = weight;
    }
}
