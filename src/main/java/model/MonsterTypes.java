package model;

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

    VECTOR { // committing crimes with both direction and magnitude
        @Override
        public String toString() {
            return "Vector";
        }
    }

}
