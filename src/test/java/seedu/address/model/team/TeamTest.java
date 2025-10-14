package seedu.address.model.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.team.exceptions.TeamMaxCapacityException;
import seedu.address.testutil.PersonBuilder;

public class TeamTest {

    private final Team team = new Team("Team1");

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Team(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Team(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Team.isValidName(null));

        // invalid name
        assertFalse(Team.isValidName("")); // empty string
        assertFalse(Team.isValidName(" ")); // spaces only
        assertFalse(Team.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Team.isValidName("team*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Team.isValidName("Team1")); // alphanumeric characters
        assertTrue(Team.isValidName("Team Alpha")); // alphanumeric characters and spaces
        assertTrue(Team.isValidName("A")); // single character
        assertTrue(Team.isValidName("Team 123")); // alphanumeric with spaces
    }

    @Test
    public void getName() {
        assertEquals("Team1", team.getName());
    }

    @Test
    public void hasPerson_personInTeam_returnsTrue() {
        team.addPerson(ALICE);
        assertTrue(team.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personNotInTeam_returnsFalse() {
        assertFalse(team.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> team.hasPerson(null));
    }

    @Test
    public void addPerson_validPerson_success() {
        team.addPerson(ALICE);
        assertTrue(team.hasPerson(ALICE));
        assertEquals(1, team.getPersonList().size());
    }

    @Test
    public void addPerson_duplicatePerson_throwsDuplicatePersonException() {
        team.addPerson(ALICE);
        assertThrows(Exception.class, () -> team.addPerson(ALICE));
    }

    @Test
    public void addPerson_teamAtMaxCapacity_throwsTeamMaxCapacityException() {
        // Add 5 people to reach max capacity
        for (int i = 0; i < 5; i++) {
            Person person = new PersonBuilder().withName("Person" + i).withEmail("testtest" + i + "@example.com").build();
            team.addPerson(person);
        }

        // Try to add one more person
        Person extraPerson = new PersonBuilder().withName("Extra Person").build();
        assertThrows(TeamMaxCapacityException.class, () -> team.addPerson(extraPerson));
    }

    @Test
    public void removePerson_personInTeam_success() {
        team.addPerson(ALICE);
        team.removePerson(ALICE);
        assertFalse(team.hasPerson(ALICE));
        assertEquals(0, team.getPersonList().size());
    }

    @Test
    public void removePerson_personNotInTeam_throwsPersonNotFoundException() {
        assertThrows(Exception.class, () -> team.removePerson(ALICE));
    }

    @Test
    public void setPersons_validList_success() {
        List<Person> persons = Arrays.asList(ALICE, BOB);
        team.setPersons(persons);
        assertTrue(team.hasPerson(ALICE));
        assertTrue(team.hasPerson(BOB));
        assertEquals(2, team.getPersonList().size());
    }

    @Test
    public void setPersons_listExceedsMaxCapacity_throwsTeamMaxCapacityException() {
        // Create a list with 6 people (exceeds max capacity of 5)
        List<Person> tooManyPersons = Arrays.asList(
            new PersonBuilder().withName("Person1").build(),
            new PersonBuilder().withName("Person2").build(),
            new PersonBuilder().withName("Person3").build(),
            new PersonBuilder().withName("Person4").build(),
            new PersonBuilder().withName("Person5").build(),
            new PersonBuilder().withName("Person6").build()
        );
        assertThrows(TeamMaxCapacityException.class, () -> team.setPersons(tooManyPersons));
    }

    @Test
    public void setPersons_emptyList_success() {
        team.addPerson(ALICE);
        team.setPersons(Collections.emptyList());
        assertEquals(0, team.getPersonList().size());
    }

    @Test
    public void equals() {
        Team team1 = new Team("Team1");
        Team team1Copy = new Team("Team1");
        Team team2 = new Team("Team2");
        // same object -> returns true
        assertTrue(team1.equals(team1));
        // same name -> returns true
        assertTrue(team1.equals(team1Copy));
        // different types -> returns false
        assertFalse(team1.equals(1));
        // null -> returns false
        assertFalse(team1.equals(null));
        // different name -> returns false
        assertFalse(team1.equals(team2));
    }

    @Test
    public void hashCode_differentTeams_differentHashCodes() {
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        assertNotEquals(team1.hashCode(), team2.hashCode());
    }

    @Test
    public void toStringMethod() {
        String expected = Team.class.getCanonicalName() + "{name=Team1, persons=" + team.getPersonList() + "}";
        assertEquals(expected, team.toString());
    }
}
