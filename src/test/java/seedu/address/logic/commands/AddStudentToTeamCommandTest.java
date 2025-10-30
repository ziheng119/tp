package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
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
    public void constructor_nullPersonIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudentToTeamCommand(null, "Team1"));
    }

    @Test
    public void constructor_nullTeamName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudentToTeamCommand(Index.fromOneBased(1), null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        AddStudentToTeamCommand command = new AddStudentToTeamCommand(Index.fromOneBased(999), "F12-3");
        // Create team first
        Team team = new Team("F12-3");
        model.addTeam(team);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, ()
                -> command.execute(model));
    }

    @Test
    public void execute_teamNotFound_throwsCommandException() {
        AddStudentToTeamCommand command = new AddStudentToTeamCommand(Index.fromOneBased(1),
                "M09-4");
        assertThrows(CommandException.class, String.format(AddStudentToTeamCommand.MESSAGE_TEAM_NOT_FOUND,
                "M09-4"), () -> command.execute(model));
    }

    @Test
    public void execute_personAlreadyInTeam_throwsCommandException() throws CommandException {
        // Create team and add person
        Team team = new Team("F12-3");
        model.addTeam(team);
        model.addPersonToTeam(ALICE, team);
        AddStudentToTeamCommand command = new AddStudentToTeamCommand(Index.fromOneBased(1), "F12-3");
        assertThrows(CommandException.class, String.format(AddStudentToTeamCommand.MESSAGE_PERSON_ALREADY_IN_TEAM,
                Messages.format(ALICE), "F12-3"), () -> command.execute(model));
    }

    @Test
    public void execute_teamAtMaxCapacity_throwsCommandException() throws CommandException {
        // Create team and fill it to capacity
        Team team = new Team("F12-3");
        model.addTeam(team);
        // Add 5 people to reach max capacity
        for (int i = 0; i < 5; i++) {
            Person person = new PersonBuilder().withName("Person" + i)
                    .withEmail("testtest" + i + "@example.com")
                    .withPhone("9999999" + i)
                    .withGithub("testuser" + i).build();
            model.addPerson(person);
            model.addPersonToTeam(person, team);
        }
        AddStudentToTeamCommand command = new AddStudentToTeamCommand(Index.fromOneBased(1), "F12-3");
        assertThrows(CommandException.class, String.format(AddStudentToTeamCommand.MESSAGE_TEAM_FULL,
                "F12-3"), () -> command.execute(model));
    }

    @Test
    public void execute_validInput_success() throws CommandException {
        Team team = new Team("F12-3");
        model.addTeam(team);
        AddStudentToTeamCommand command = new AddStudentToTeamCommand(Index.fromOneBased(1), "F12-3");
        CommandResult result = command.execute(model);
        assertEquals(String.format(AddStudentToTeamCommand.MESSAGE_SUCCESS, Messages.format(ALICE), "F12-3"),
                result.getFeedbackToUser());
        assertTrue(team.hasPerson(ALICE));
    }

    @Test
    public void execute_addToNoneTeam_throwsCommandException() {
        // Add the sentinel NONE team explicitly into the model
        model.addTeam(Team.NONE);
        AddStudentToTeamCommand command = new AddStudentToTeamCommand(Index.fromOneBased(1), "");
        assertThrows(CommandException.class, AddStudentToTeamCommand
            .MESSAGE_TEAM_INVALID_NONE, () -> command.execute(model));
    }

    @Test
    public void execute_personInAnotherTeam_throwsCommandException() throws CommandException {
        // Create two teams
        Team teamA = new Team("F12-3");
        Team teamB = new Team("M09-4");
        model.addTeam(teamA);
        model.addTeam(teamB);

        // Put ALICE into teamA
        model.addPersonToTeam(ALICE, teamA);

        // Attempt to add ALICE (index 1) to teamB
        AddStudentToTeamCommand command = new AddStudentToTeamCommand(Index.fromOneBased(1), "M09-4");

        assertThrows(CommandException.class,
                String.format(AddStudentToTeamCommand.MESSAGE_PERSON_IN_ANOTHER_TEAM, Messages.format(ALICE),
                        teamA.getName()), () -> command.execute(model));
    }

    @Test
    public void execute_secondPersonInList_success() throws CommandException {
        Team team = new Team("F12-3");
        model.addTeam(team);
        // Use index 2 to add BOB (second person in the list)
        AddStudentToTeamCommand command = new AddStudentToTeamCommand(Index.fromOneBased(2), "F12-3");
        CommandResult result = command.execute(model);
        assertEquals(String.format(AddStudentToTeamCommand.MESSAGE_SUCCESS, Messages.format(BOB), "F12-3"),
                result.getFeedbackToUser());
        assertTrue(team.hasPerson(BOB));
    }

    @Test
    public void equals() {
        AddStudentToTeamCommand command1 = new AddStudentToTeamCommand(Index.fromOneBased(1), "Team1");
        AddStudentToTeamCommand command2 = new AddStudentToTeamCommand(Index.fromOneBased(1), "Team1");
        AddStudentToTeamCommand command3 = new AddStudentToTeamCommand(Index.fromOneBased(2), "Team1");
        AddStudentToTeamCommand command4 = new AddStudentToTeamCommand(Index.fromOneBased(1), "Team2");
        // same object -> returns true
        assertTrue(command1.equals(command1));
        // same values -> returns true
        assertTrue(command1.equals(command2));
        // different types -> returns false
        assertFalse(command1.equals(1));
        // null -> returns false
        assertFalse(command1.equals(null));
        // different person index -> returns false
        assertFalse(command1.equals(command3));
        // different team name -> returns false
        assertFalse(command1.equals(command4));
    }

    @Test
    public void toStringMethod() {
        AddStudentToTeamCommand command = new AddStudentToTeamCommand(Index.fromOneBased(1), "Team1");
        String expected = AddStudentToTeamCommand.class.getCanonicalName() + "{studentIndex="
                + Index.fromOneBased(1) + ", teamName=Team1}";
        assertEquals(expected, command.toString());
    }
}
