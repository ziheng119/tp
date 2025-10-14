package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.team.Team;

public class JsonAdaptedTeamTest {

    private static final String VALID_TEAM_NAME = "TeamAlpha";

    private static final Name ALICE_NAME = new Name("Alice");
    private static final Phone ALICE_PHONE = new Phone("12345678");
    private static final Email ALICE_EMAIL = new Email("alice@example.com");
    private static final Github ALICE_GITHUB = new Github("alicegh");

    private static final Name BOB_NAME = new Name("Bob");
    private static final Phone BOB_PHONE = new Phone("87654321");
    private static final Email BOB_EMAIL = new Email("bob@example.com");
    private static final Github BOB_GITHUB = new Github("bobgh");

    private static final Person alice = new Person(ALICE_NAME, ALICE_PHONE, ALICE_EMAIL, ALICE_GITHUB);
    private static final Person bob = new Person(BOB_NAME, BOB_PHONE, BOB_EMAIL, BOB_GITHUB);

    @Test
    public void toModelType_validTeam_success() throws Exception {
        List<Email> memberEmails = Arrays.asList(ALICE_EMAIL, BOB_EMAIL);
        JsonAdaptedTeam jsonTeam = new JsonAdaptedTeam(VALID_TEAM_NAME, memberEmails);

        ObservableList<Person> allPersons = FXCollections.observableArrayList(alice, bob);
        Team team = jsonTeam.toModelType(allPersons);

        assertEquals(VALID_TEAM_NAME, team.getName());
        assertEquals(2, team.getPersonList().size());
        assertTrue(team.getPersonList().containsAll(Arrays.asList(alice, bob)));
    }

    @Test
    public void toModelType_missingTeamName_throwsIllegalValueException() {
        List<Email> memberEmails = List.of(ALICE_EMAIL);
        JsonAdaptedTeam jsonTeam = new JsonAdaptedTeam(null, memberEmails);

        ObservableList<Person> allPersons = FXCollections.observableArrayList(alice);

        assertThrows(IllegalValueException.class, () -> jsonTeam.toModelType(allPersons));
    }

    @Test
    public void toModelType_memberNotInAddressBook_throwsIllegalValueException() {
        List<Email> memberEmails = Arrays.asList(ALICE_EMAIL, new Email("notfound@example.com"));
        JsonAdaptedTeam jsonTeam = new JsonAdaptedTeam(VALID_TEAM_NAME, memberEmails);

        ObservableList<Person> allPersons = FXCollections.observableArrayList(alice);

        assertThrows(IllegalValueException.class, () -> jsonTeam.toModelType(allPersons));
    }

    @Test
    public void constructor_nullMembers_initializesEmptyList() throws Exception {
        JsonAdaptedTeam jsonTeam = new JsonAdaptedTeam(VALID_TEAM_NAME, null);

        ObservableList<Person> allPersons = FXCollections.observableArrayList();
        Team team = jsonTeam.toModelType(allPersons);

        assertEquals(0, team.getPersonList().size());
    }
}