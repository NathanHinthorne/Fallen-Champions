package model;

public enum Pillars {
    ABSTRACTION {
        @Override
        public String toString() {
            return "A";
        }
    },
    ENCAPSULATION {
        @Override
        public String toString() {
            return "E";
        }
    },
    INHERITANCE {
        @Override
        public String toString() {
            return "I";
        }
    },
    POLYMORPHISM {
        @Override
        public String toString() {
            return "P";
        }
    };
}
