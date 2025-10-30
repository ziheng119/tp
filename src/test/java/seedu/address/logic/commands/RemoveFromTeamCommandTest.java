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
import seedu.address.model.team.Team;
import seedu.address.testutil.AddressBookBuilder;

public class RemoveFromTeamCommandTest {

    private Model model = new ModelManager(new AddressBookBuilder().withPerson(ALICE).withPerson(BOB).build(),
            new UserPrefs());

    @Test
    public void constructor_nullPersonIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveFromTeamCommand(null, "Team1"));
    }

    @Test
    public void constructor_nullTeamName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveFromTeamCommand(Index.fromOneBased(1),
                null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        RemoveFromTeamCommand command = new RemoveFromTeamCommand(Index.fromOneBased(999), "F12-3");
        // Create team first
        Team team = new Team("F12-3");
        model.addTeam(team);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, ()
                -> command.execute(model));
    }

    @Test
    public void execute_teamNotFound_throwsCommandException() {
        RemoveFromTeamCommand command = new RemoveFromTeamCommand(Index.fromOneBased(1),
                "M09-4");
        assertThrows(CommandException.class, String.format(RemoveFromTeamCommand.MESSAGE_TEAM_NOT_FOUND,
                "M09-4"), () -> command.execute(model));
    }

    @Test
    public void execute_personNotInTeam_throwsCommandException() throws CommandException {
        // Create team but don't add person to it
        Team team = new Team("F12-3");
        model.addTeam(team);
        RemoveFromTeamCommand command = new RemoveFromTeamCommand(Index.fromOneBased(1), "F12-3");
        assertThrows(CommandException.class, String.format(RemoveFromTeamCommand.MESSAGE_PERSON_NOT_IN_TEAM,
                Messages.format(ALICE), "F12-3"), () -> command.execute(model));
    }

    @Test
    public void execute_validInput_success() throws CommandException {
        // Create team and add person
        Team team = new Team("F12-3");
        model.addTeam(team);
        model.addPersonToTeam(ALICE, team);
        // Verify person is in team
        assertTrue(team.hasPerson(ALICE));
        RemoveFromTeamCommand command = new RemoveFromTeamCommand(Index.fromOneBased(1), "F12-3");
        CommandResult result = command.execute(model);
        assertEquals(String.format(RemoveFromTeamCommand.MESSAGE_SUCCESS, Messages.format(ALICE), "F12-3"),
                result.getFeedbackToUser());
        assertFalse(team.hasPerson(ALICE));
    }

    @Test
    public void execute_secondPersonInList_success() throws CommandException {
        // Create team and add BOB
        Team team = new Team("F12-3");
        model.addTeam(team);
        model.addPersonToTeam(BOB, team);
        // Use index 2 to remove BOB (second person in the list)
        RemoveFromTeamCommand command = new RemoveFromTeamCommand(Index.fromOneBased(2), "F12-3");
        CommandResult result = command.execute(model);
        assertEquals(String.format(RemoveFromTeamCommand.MESSAGE_SUCCESS, Messages.format(BOB), "F12-3"),
                result.getFeedbackToUser());
        assertFalse(team.hasPerson(BOB));
    }

    @Test
    public void execute_removeFromEmptyTeam_throwsCommandException() throws CommandException {
        // Create empty team
        Team team = new Team("F12-3");
        model.addTeam(team);
        RemoveFromTeamCommand command = new RemoveFromTeamCommand(Index.fromOneBased(1), "F12-3");
        assertThrows(CommandException.class, String.format(RemoveFromTeamCommand.MESSAGE_PERSON_NOT_IN_TEAM,
                Messages.format(ALICE), "F12-3"), () -> command.execute(model));
    }

    @Test
    public void execute_removeFromNoneTeam_throwsCommandException() {
        // Add the sentinel NONE team explicitly into the model
        model.addTeam(Team.NONE);
        RemoveFromTeamCommand command = new RemoveFromTeamCommand(Index.fromOneBased(1), "");
        assertThrows(CommandException.class, RemoveFromTeamCommand
            .MESSAGE_CANNOT_REMOVE_FROM_NONE, () -> command.execute(model));
    }

    @Test
    public void execute_personInDifferentTeam_throwsCommandException() throws CommandException {
        // Create two teams
        Team teamA = new Team("F12-3");
        Team teamB = new Team("M09-4");
        model.addTeam(teamA);
        model.addTeam(teamB);

        // Put ALICE into teamA
        model.addPersonToTeam(ALICE, teamA);

        // Attempt to remove ALICE from teamB
        RemoveFromTeamCommand command = new RemoveFromTeamCommand(Index.fromOneBased(1), "M09-4");
        assertThrows(CommandException.class, String.format(RemoveFromTeamCommand.MESSAGE_PERSON_NOT_IN_TEAM,
                Messages.format(ALICE), "M09-4"), () -> command.execute(model));
    }

    @Test
    public void equals() {
        RemoveFromTeamCommand command1 = new RemoveFromTeamCommand(Index.fromOneBased(1), "F12-3");
        RemoveFromTeamCommand command2 = new RemoveFromTeamCommand(Index.fromOneBased(1), "F12-3");
        RemoveFromTeamCommand command3 = new RemoveFromTeamCommand(Index.fromOneBased(2), "F12-3");
        RemoveFromTeamCommand command4 = new RemoveFromTeamCommand(Index.fromOneBased(1), "W08-1");
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
        RemoveFromTeamCommand command = new RemoveFromTeamCommand(Index.fromOneBased(1), "Team1");
        String expected = RemoveFromTeamCommand.class.getCanonicalName() + "{personIndex="
                + Index.fromOneBased(1) + ", teamName=Team1}";
        assertEquals(expected, command.toString());
    }
}
