package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Team} name matches the given team name.
 */
public class TeamNamePredicate implements Predicate<Person> {
    private final String teamName;

    public TeamNamePredicate(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public boolean test(Person person) {
        assert person != null;
        return person.getTeamName().equalsIgnoreCase(teamName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TeamNamePredicate)) {
            return false;
        }

        TeamNamePredicate otherTeamNamePredicate = (TeamNamePredicate) other;
        return teamName.equals(otherTeamNamePredicate.teamName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("teamName", teamName).toString();
    }
}
