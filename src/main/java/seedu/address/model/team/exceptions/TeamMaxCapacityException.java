package seedu.address.model.team.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class TeamMaxCapacityException extends RuntimeException {
    public TeamMaxCapacityException() {
        super("Operation would exceed team capacity");
    }
}

