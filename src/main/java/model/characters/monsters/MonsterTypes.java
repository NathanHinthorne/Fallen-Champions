package model.characters.monsters;

/**
 * Enumerated type for the monster types.
 */
public enum MonsterTypes {
    SKELETON {
        @Override
        public String toString() {
            return "Skeleton";
        }
    },
    OGRE {
        @Override
        public String toString() {
            return "Ogre";
        }
    },
    GREMLIN {
        @Override
        public String toString() {
            return "Gremlin";
        }
    },
    WARLOCK {
        @Override
        public String toString() {
            return "Warlock";
        }
    },
    SPIDER {
        @Override
        public String toString() {
            return "Giant Spider";
        }
    },
    DRAGON { // dastardly, dragonized Davy
        @Override
        public String toString() {
            return "Dragon";
        }
    },

    SUPER_GREMLIN { // a bunch of gremlins in a trench coat (or just all bunched up). a final boss in hardcore
        @Override
        public String toString() {
            return "Super Gremlin";
        }
    }

}
