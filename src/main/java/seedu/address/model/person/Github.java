package main.java.seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's GitHub username in the address book. Guarantees: immutable; is valid as
 * declared in {@link #isValidGithub(String)}
 */
public class Github {

    public static final String MESSAGE_CONSTRAINTS =
            "GitHub usernames must be 1-39 characters, alphanumeric or hyphens, "
                    + "cannot start or end with a hyphen, and cannot have consecutive hyphens.";

    // Regex adapted from GitHub username rules
    public static final String VALIDATION_REGEX = "^(?!-)(?!.*--)[A-Za-z0-9-]{1,39}(?<!-)$";

    public final String value;

    /**
     * Constructs a {@code Github}.
     *
     * @param githubUsername A valid GitHub username.
     */
    public Github(String githubUsername) {
        requireNonNull(githubUsername);
        if (!isValidGithub(githubUsername)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        value = githubUsername;
    }

    /**
     * Returns true if a given string is a valid GitHub username.
     */
    public static boolean isValidGithub(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Github && value.equals(((Github) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
