package kuro.exceptions;

/**
 * A specific exception for kuro.
 */
public class KuroException extends Exception {
        public KuroException(String message) {
            super(message);
        }

        @Override
        public String toString() {
            return "Error while interacting with Kuro: " + getMessage();
        }
}
