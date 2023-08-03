package model;

public enum Parchment {

    // in order of obtainability (and worst to best)
    NO_COOLDOWNS {
        @Override
        public String toString() {
            return "No Cooldowns";
        }
    },

    BURST_ATTACK { // attack 3 times in a row on your turn (like undertale knife attack)
        @Override
        public String toString() {
            return "Burst Attack";
        }
    },

    PICK_UP_WALLS {
        @Override
        public String toString() {
            return "Pick Up Walls";
        }
    },

    INFINITE_POTIONS {
        @Override
        public String toString() {
            return "Infinite Potions";
        }
    },

    ENEMY_ALWAYS_STUCK {
        @Override
        public String toString() {
            return "Enemy Always Stuck";
        }
    },

    INSANE_DAMAGE {
        @Override
        public String toString() {
            return "Insane Damage";
        }
    },

    INFINITE_HEALTH {
        @Override
        public String toString() {
            return "Infinite Health";
        }
    },

    /*
    storyline:
        Dungeon keeper wants to keep you in the game.
        He also wants the game to run as intended, with no glitches.
        ...more goals?

        As you start to collect parchments, he tries to hide the fact
        that you can use the inscriptions as cheat codes.
        (1 parchment per dungeon)

        He also gets more and more angry whenever you find access
        a "portion of game code" inside a room. ex: "you didn't find anything suspicious in there, did you?"
        Somehow he can always tell when you found one.
        (can be more than 1 "portion of game code" in a dungeon. percentage increases as dungeonsBeaten increase)

        After hard mode (or after some other part) the dungeon keeper will
        battle you. He will reluctantly alter the game code himself to make himself
        more powerful with cheats in order to beat you.





     */

}
