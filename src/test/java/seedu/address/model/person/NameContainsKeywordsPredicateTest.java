package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.team.Team;
import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList,
                Collections.emptyList());
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList,
                Collections.emptyList());

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList,
                Collections.emptyList());
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_teamContainsKeywords_returnsTrue() {
        // team-only search should match when team keyword present
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList(),
                Collections.singletonList("F12-3"));
        assertTrue(predicate.test(new PersonBuilder().withName("Someone").withTeam(new Team("F12-3")).build()));

        // multiple team keywords
        predicate = new NameContainsKeywordsPredicate(Collections.emptyList(), Arrays.asList("F12-3", "W08-1"));
        assertTrue(predicate.test(new PersonBuilder().withName("Someone").withTeam(new Team("W08-1")).build()));
    }

    @Test
    public void test_nameAndTeamCombined_returnsTrueOrFalse() {
        // both provided: both must match
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"),
                Collections.singletonList("F12-3"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTeam(new Team("F12-3")).build()));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTeam(new Team("T14-2")).build()));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withTeam(new Team("F12-3")).build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords -> no match
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList(),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carol"), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and github, but does not match name/team
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withGithub("alice").build()));
    }

    @Test
    public void toStringMethod_containsKeywords() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords, Collections.emptyList());

        // just verify the predicate's toString mentions the keywords
        assertTrue(predicate.toString().contains("keyword1"));
        assertTrue(predicate.toString().contains("keyword2"));
    }
}
