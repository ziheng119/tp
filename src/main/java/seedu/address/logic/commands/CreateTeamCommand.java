package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.team.Team;

/**
 * Creates a team with the input name.
 */
public class CreateTeamCommand extends Command {

    public static final String COMMAND_WORD = "create_team";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a team with a name specified by the input.\n"
            + "Parameters: NAME (must be a valid string) "
            + "Example: " + COMMAND_WORD + " Team_1";

    public static final String MESSAGE_CREATE_TEAM_SUCESS = "Created Team: %s";

    private final String teamName;

    /**
     * @param teamName name of the team
     */
    public CreateTeamCommand(String teamName) {
        requireAllNonNull(teamName);

        this.teamName = teamName;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTeamWithName(this.teamName)) {
            throw new CommandException("Team with name '" + this.teamName + "' already exists");
        }

        Team team = new Team(this.teamName);
        model.addTeam(team);

        return new CommandResult(generateSuccessMessage(this.teamName));
    }

    /**
     * Generates a command execution success message with team name
     */
    private String generateSuccessMessage(String teamName) {
        return String.format(MESSAGE_CREATE_TEAM_SUCESS, teamName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CreateTeamCommand)) {
            return false;
        }

        // state check
        CreateTeamCommand e = (CreateTeamCommand) other;
        return teamName.equals(e.teamName);
    }
}
