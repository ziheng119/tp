package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final List<String> teamKeywords;

    /**
     * Creates a predicate to test for name and team keyword matches.
     *
     * @param nameKeywords List of keywords to match against person names. Empty list means no name filtering.
     * @param teamKeywords List of keywords to match against team names. Empty list means no team filtering.
     */
    public NameContainsKeywordsPredicate(List<String> nameKeywords, List<String> teamKeywords) {
        this.nameKeywords = nameKeywords;
        this.teamKeywords = teamKeywords;
    }

    /**
     * Tests if a given person matches both the name and team keywords (if any).
     * A person matches if:
     * - Their name contains any of the name keywords (or no name keywords were provided) AND
     * - Their team contains any of the team keywords (or no team keywords were provided)
     *
     * @param person the person to test
     * @return true if the person matches both name and team criteria
     */
    @Override
    public boolean test(Person person) {
        List<String> validNameKeywords = nameKeywords.stream()
        .filter(keyword -> !keyword.isBlank())
        .toList();

        List<String> validTeamKeywords = teamKeywords.stream()
        .filter(keyword -> !keyword.isBlank())
        .toList();

        if (validNameKeywords.isEmpty() && validTeamKeywords.isEmpty()) {
        return false;
        }

        boolean nameMatches = validNameKeywords.isEmpty()
                || validNameKeywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));

        boolean teamMatches = validTeamKeywords.isEmpty()
                || validTeamKeywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTeamName(), keyword));

        return nameMatches && teamMatches;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return nameKeywords.equals(otherNameContainsKeywordsPredicate.nameKeywords)
                && teamKeywords.equals(otherNameContainsKeywordsPredicate.teamKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("teamKeywords", teamKeywords)
                .toString();
    }
}
