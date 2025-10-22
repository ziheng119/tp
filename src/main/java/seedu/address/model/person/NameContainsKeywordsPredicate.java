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

    public NameContainsKeywordsPredicate(List<String> nameKeywords, List<String> teamKeywords) {
        this.nameKeywords = nameKeywords;
        this.teamKeywords = teamKeywords;
    }

    @Override
    public boolean test(Person person) {
        boolean nameMatches = nameKeywords.isEmpty() 
                || nameKeywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));

        boolean teamMatches = teamKeywords.isEmpty()
                || teamKeywords.stream()
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
