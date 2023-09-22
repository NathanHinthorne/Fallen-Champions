package FallenChampions.model.dungeon;

/**
 * Enumerated type for the difficulty of the dungeon.
 * Used in the DungeonBuilder class.
 *
 * @author Nathan Hinthorne
 * @version 1.0 - 6/9/23
 */
public enum Difficulty {
    EASY {
        @Override
        public String toString() {
            return "Easy";
        }
    },

    MEDIUM {
        @Override
        public String toString() {
            return "Medium";
        }
    },

    HARD {
        @Override
        public String toString() {
            return "Hard";
        }
    };

    public static Difficulty getDifficulty(Character userInput) {
        switch (userInput) {
            case '1':
                return EASY;
            case '2':
                return MEDIUM;
            case '3':
                return HARD;
            default:
                return null;
        }
    }
}
