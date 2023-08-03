package model;

public enum Debuff {
    BLIND { //blindable
        @Override
        public String toString() {
            return "BLINDED";
        }
    },
    POISON { // poisonable
        @Override
        public String toString() {
            return "POISONED";
        }
    },
    SILENCE { // silencable
        @Override
        public String toString() {
            return "SILENCED";
        }
    },
    STUCKIFY { // stuckable
        @Override
        public String toString() {
            return "STUCK";
        }
    },
    VULNERATE { // vulnerable,
        @Override
        public String toString() {
            return "VULNERABLE";
        }
    },
    WEAKEN { // weakenable
        @Override
        public String toString() {
            return "WEAKENED";
        }
    },

}
