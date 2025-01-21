package backend.academy;

import backend.academy.mazeTask.maze.MazeMenu;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        new MazeMenu().start();
    }
}
