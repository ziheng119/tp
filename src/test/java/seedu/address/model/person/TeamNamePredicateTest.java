package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.team.Team;
import seedu.address.testutil.PersonBuilder;

public class TeamNamePredicateTest {

    @Test
    public void equals() {
        TeamNamePredicate firstPredicate = new TeamNamePredicate("F12-3");
        TeamNamePredicate secondPredicate = new TeamNamePredicate("W08-1");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TeamNamePredicate firstPredicateCopy = new TeamNamePredicate("F12-3");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different team -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_teamNameMatches_returnsTrue() {
        TeamNamePredicate predicate = new TeamNamePredicate("F12-3");
        assertTrue(predicate.test(new PersonBuilder().withTeam(new Team("F12-3")).build()));
    }

    @Test
    public void test_teamNameDoesNotMatch_returnsFalse() {
        TeamNamePredicate predicate = new TeamNamePredicate("F12-3");
        assertFalse(predicate.test(new PersonBuilder().withTeam(new Team("W08-1")).build()));
    }
}
