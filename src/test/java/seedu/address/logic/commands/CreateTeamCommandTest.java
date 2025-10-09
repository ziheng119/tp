package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.team.Team;
import seedu.address.testutil.AddressBookBuilder;

public class CreateTeamCommandTest {

    private Model model = new ModelManager(new AddressBookBuilder().build(), new UserPrefs());

    @Test
    public void constructor_nullTeamName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreateTeamCommand(null));
    }

    @Test
    public void execute_teamCreated_success() throws CommandException {
        CreateTeamCommand command = new CreateTeamCommand("Team1");
        CommandResult result = command.execute(model);

        assertEquals(String.format(CreateTeamCommand.MESSAGE_CREATE_TEAM_SUCESS, "Team1"), result.getFeedbackToUser());
        assertTrue(model.hasTeamWithName("Team1"));
    }

    @Test
    public void execute_duplicateTeamName_throwsCommandException() throws CommandException {
        // Create first team
        Team team1 = new Team("Team1");
        model.addTeam(team1);

        CreateTeamCommand command = new CreateTeamCommand("Team1");
        assertThrows(CommandException.class, "Team with name 'Team1' already exists", () -> command.execute(model));
    }

    @Test
    public void execute_multipleTeamsCreated_success() throws CommandException {
        CreateTeamCommand command1 = new CreateTeamCommand("Team1");
        CreateTeamCommand command2 = new CreateTeamCommand("Team2");

        command1.execute(model);
        command2.execute(model);

        assertTrue(model.hasTeamWithName("Team1"));
        assertTrue(model.hasTeamWithName("Team2"));
    }

    @Test
    public void execute_teamWithSpacesInName_success() throws CommandException {
        CreateTeamCommand command = new CreateTeamCommand("Team Alpha");
        CommandResult result = command.execute(model);

        assertEquals(String.format(CreateTeamCommand.MESSAGE_CREATE_TEAM_SUCESS, "Team Alpha"),
                result.getFeedbackToUser());
        assertTrue(model.hasTeamWithName("Team Alpha"));
    }

    @Test
    public void execute_teamWithNumbersInName_success() throws CommandException {
        CreateTeamCommand command = new CreateTeamCommand("Team123");
        CommandResult result = command.execute(model);

        assertEquals(String.format(CreateTeamCommand.MESSAGE_CREATE_TEAM_SUCESS, "Team123"),
                result.getFeedbackToUser());
        assertTrue(model.hasTeamWithName("Team123"));
    }

    @Test
    public void equals() {
        CreateTeamCommand command1 = new CreateTeamCommand("Team1");
        CreateTeamCommand command2 = new CreateTeamCommand("Team1");
        CreateTeamCommand command3 = new CreateTeamCommand("Team2");

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        assertTrue(command1.equals(command2));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different team name -> returns false
        assertFalse(command1.equals(command3));
    }

    @Test
    public void toStringMethod() {
        CreateTeamCommand command = new CreateTeamCommand("Team1");
        String expected = CreateTeamCommand.class.getCanonicalName() + "{teamName=Team1}";
        assertEquals(expected, command.toString());
    }
}
