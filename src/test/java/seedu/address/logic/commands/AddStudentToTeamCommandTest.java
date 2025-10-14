package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddStudentToTeamCommandTest {

    private Model model = new ModelManager(new AddressBookBuilder().withPerson(ALICE).withPerson(BOB).build(),
            new UserPrefs());

    @Test
    public void constructor_nullPersonName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudentToTeamCommand(null, "Team1"));
    }

    @Test
    public void constructor_nullTeamName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudentToTeamCommand("John Doe", null));
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        AddStudentToTeamCommand command = new AddStudentToTeamCommand("NonExistent", "Team1");
        // Create team first
        Team team = new Team("Team1");
        model.addTeam(team);
        assertThrows(CommandException.class, String.format(AddStudentToTeamCommand.MESSAGE_PERSON_NOT_FOUND,
                "NonExistent"), () -> command.execute(model));
    }

    @Test
    public void execute_teamNotFound_throwsCommandException() {
        AddStudentToTeamCommand command = new AddStudentToTeamCommand(ALICE.getName().toString(),
                "NonExistentTeam");
        assertThrows(CommandException.class, String.format(AddStudentToTeamCommand.MESSAGE_TEAM_NOT_FOUND,
                "NonExistentTeam"), () -> command.execute(model));
    }

    @Test
    public void execute_personAlreadyInTeam_throwsCommandException() throws CommandException {
        // Create team and add person
        Team team = new Team("Team1");
        model.addTeam(team);
        model.addPersonToTeam(ALICE, team);
        AddStudentToTeamCommand command = new AddStudentToTeamCommand(ALICE.getName().toString(), "Team1");
        assertThrows(CommandException.class, String.format(AddStudentToTeamCommand.MESSAGE_PERSON_ALREADY_IN_TEAM,
                Messages.format(ALICE), "Team1"), () -> command.execute(model));
    }

    @Test
    public void execute_teamAtMaxCapacity_throwsCommandException() throws CommandException {
        // Create team and fill it to capacity
        Team team = new Team("Team1");
        model.addTeam(team);
        // Add 5 people to reach max capacity
        for (int i = 0; i < 5; i++) {
            Person person = new PersonBuilder().withName("Person" + i)
                    .withEmail("testtest" + i + "@example.com").build();
            model.addPerson(person);
            model.addPersonToTeam(person, team);
        }
        AddStudentToTeamCommand command = new AddStudentToTeamCommand(ALICE.getName().toString(), "Team1");
        assertThrows(CommandException.class, String.format(AddStudentToTeamCommand.MESSAGE_TEAM_FULL,
                "Team1"), () -> command.execute(model));
    }

    @Test
    public void execute_validInput_success() throws CommandException {
        Team team = new Team("Team1");
        model.addTeam(team);
        AddStudentToTeamCommand command = new AddStudentToTeamCommand(ALICE.getName().toString(), "Team1");
        CommandResult result = command.execute(model);
        assertEquals(String.format(AddStudentToTeamCommand.MESSAGE_SUCCESS, Messages.format(ALICE), "Team1"),
                result.getFeedbackToUser());
        assertTrue(team.hasPerson(ALICE));
    }

    @Test
    public void execute_caseInsensitiveName_success() throws CommandException {
        Team team = new Team("Team1");
        model.addTeam(team);
        // Use lowercase version of ALICE's name
        String lowerCaseName = ALICE.getName().toString().toLowerCase();
        AddStudentToTeamCommand command = new AddStudentToTeamCommand(lowerCaseName, "Team1");
        CommandResult result = command.execute(model);
        assertEquals(String.format(AddStudentToTeamCommand.MESSAGE_SUCCESS, Messages.format(ALICE), "Team1"),
                result.getFeedbackToUser());
        assertTrue(team.hasPerson(ALICE));
    }

    @Test
    public void equals() {
        AddStudentToTeamCommand command1 = new AddStudentToTeamCommand("John Doe", "Team1");
        AddStudentToTeamCommand command2 = new AddStudentToTeamCommand("John Doe", "Team1");
        AddStudentToTeamCommand command3 = new AddStudentToTeamCommand("Jane Doe", "Team1");
        AddStudentToTeamCommand command4 = new AddStudentToTeamCommand("John Doe", "Team2");
        // same object -> returns true
        assertTrue(command1.equals(command1));
        // same values -> returns true
        assertTrue(command1.equals(command2));
        // different types -> returns false
        assertFalse(command1.equals(1));
        // null -> returns false
        assertFalse(command1.equals(null));
        // different person name -> returns false
        assertFalse(command1.equals(command3));
        // different team name -> returns false
        assertFalse(command1.equals(command4));
    }

    @Test
    public void toStringMethod() {
        AddStudentToTeamCommand command = new AddStudentToTeamCommand("John Doe", "Team1");
        String expected = AddStudentToTeamCommand.class.getCanonicalName() + "{studentName=John Doe, teamName=Team1}";
        assertEquals(expected, command.toString());
    }
}
