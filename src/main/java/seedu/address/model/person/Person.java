package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.team.Team;

/**
 * Represents a Person in the address book. Guarantees: details are present and not null, field
 * values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Github github;
    private final Team team;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Github github, Team team) {
        requireAllNonNull(name, phone, email, github, team);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.github = github;
        this.team = team;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Github getGithub() {
        return github;
    }

    public Team getTeam() {
        return team;
    }

    public String getTeamName() {
        return team.getName();
    }

    /**
     * Returns true if both persons have the same name. This defines a weaker notion of equality
     * between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null && otherPerson.getEmail().equals(getEmail());
    }

    /**
     * Returns true if both persons have the same identity and data fields. This defines a stronger
     * notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && github.equals(otherPerson.github)
                && team.equals(otherPerson.team);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, github, team);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("github", github)
                .add("team", team.getName())
                .toString();
    }

}
