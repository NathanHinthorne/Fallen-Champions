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
    }
}
