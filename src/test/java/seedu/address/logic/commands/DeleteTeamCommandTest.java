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
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class DeleteTeamCommandTest {

    private Model model = new ModelManager(new AddressBookBuilder().build(), new UserPrefs());

    @Test
    public void constructor_nullTeamName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteTeamCommand(null));
    }

    @Test
    public void execute_teamDeleted_success() throws CommandException {
        Team team = new Team("F12-3");
        model.addTeam(team);

        // create a person that references the same team instance and add to model
        Person member = new PersonBuilder().withTeam(team).build();
        model.addPerson(member);

        DeleteTeamCommand command = new DeleteTeamCommand("F12-3");
        CommandResult result = command.execute(model);

        assertEquals(String.format(DeleteTeamCommand.MESSAGE_SUCCESS, "Team1"),
                result.getFeedbackToUser());
        // team removed
        assertFalse(model.hasTeamWithName("F12-3"));
        // member's team should be cleared to Team.NONE
        Person updated = model.getFilteredPersonList().stream()
                .filter(p -> p.getEmail().equals(member.getEmail()))
                .findFirst().get();
        assertTrue(updated.getTeam().equals(Team.NONE));
    }

    @Test
    public void execute_teamNotFound_throwsCommandException() throws Exception {
        DeleteTeamCommand command = new DeleteTeamCommand("NonExistent");
        assertThrows(CommandException.class, "Team with name 'NonExistent' not found", () -> command.execute(model));
    }

    @Test
    public void equals() {
        DeleteTeamCommand command1 = new DeleteTeamCommand("F12-3");
        DeleteTeamCommand command2 = new DeleteTeamCommand("F12-3");
        DeleteTeamCommand command3 = new DeleteTeamCommand("F12-4");

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
        DeleteTeamCommand command = new DeleteTeamCommand("F12-3");
        String expected = DeleteTeamCommand.class.getCanonicalName() + "{teamName=F12-3}";
        assertEquals(expected, command.toString());
    }
}
