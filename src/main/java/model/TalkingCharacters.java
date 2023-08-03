package model;

public enum TalkingCharacters {

    NONE {
        @Override
        public String toString() {
            return "Nathan";
        }
    },
    NATHAN {
        @Override
        public String toString() {
            return "Nathan";
        }
    },

    KEEPER {
        @Override
        public String toString() {
            return "Dungeon Keeper";
        }
    },

    SUPER_GREMLIN {
        @Override
        public String toString() {
            return "Super Gremlin";
        }
    },

    DAVY {
        @Override
        public String toString() {
            return "Davy";
        }
    }




}
