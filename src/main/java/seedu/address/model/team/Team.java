package seedu.address.model.team;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.team.exceptions.TeamMaxCapacityException;

/**
 * Wraps all data at the team level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class Team {

    public static final String MESSAGE_CONSTRAINTS =
            "Team names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final Team NONE = new Team();
    private static final int MAX_CAPACITY = 5;

    private final String name;
    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    // Private constructor for NONE
    private Team() {
        this.name = "";
    }

    /**
     * Constructs a {@code Team}.
     *
     * @param name A valid name.
     */
    public Team(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.name = name;
    }

    /**
     * Creates an Team using the Persons in the {@code toBeCopied}
     */
    public Team(String name, ObservableList<Person> toBeCopied) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.name = name;
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        if (persons.size() > MAX_CAPACITY) {
            throw new TeamMaxCapacityException();
        }
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code Team} with {@code newData}.
     */
    public void resetData(ObservableList<Person> newData) {
        requireNonNull(newData);

        setPersons(newData);
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the team.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the team.
     * The person must not already exist in the team.
     */
    public void addPerson(Person p) {
        if (persons.size() + 1 > MAX_CAPACITY) {
            throw new TeamMaxCapacityException();
        }
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the team.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the team.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code Team}.
     * {@code key} must exist in the team.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    public String getName() {
        return name;
    }

    //// util methods
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isNoneTeamName(String test) {
        return test.equals("");
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    /**
     * Returns true if both teams have the same name. This defines a weaker notion of equality
     * between two teams. Abstracts team comparison for UniqueTeamList.java
     */
    public boolean isSameTeam(Team otherTeam) {
        if (otherTeam == this) {
            return true;
        }
        return otherTeam != null && otherTeam.getName().equals(getName());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Team)) {
            return false;
        }

        Team otherTeam = (Team) other;
        return name.equals(otherTeam.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + persons.hashCode();
    }
}
