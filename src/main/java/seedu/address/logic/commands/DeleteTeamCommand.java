package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Deletes a team and sets all members' team to Team.NONE.
 */
public class DeleteTeamCommand extends Command {


    public static final String COMMAND_WORD = "delete-t";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a team.\n"
            + "Parameters: t/TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " t/F12-3";

    public static final String MESSAGE_SUCCESS = "Team %s deleted";
    public static final String MESSAGE_TEAM_NOT_FOUND = "Team with name '%s' not found";

    private static final Logger logger = LogsCenter.getLogger(DeleteTeamCommand.class);

    private final String teamName;

    /**
     * Creates a DeleteTeamCommand to delete the Team with the specified {@code teamName}.
     */
    public DeleteTeamCommand(String teamName) {
        requireNonNull(teamName);
        this.teamName = teamName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert teamName != null : "Ensure target team name is not null";
        logger.info("Executing " + this.getClass().getSimpleName() + "...");

        Team targetTeam = model.getTeamByName(teamName);

        if (targetTeam == null) {
            logger.warning("Error finding team with name " + teamName);
            throw new CommandException(String.format(MESSAGE_TEAM_NOT_FOUND, teamName));
        }

        List<Person> persons = model.getFilteredPersonList().stream()
                .filter(p -> p.getTeam().equals(targetTeam))
                .collect(Collectors.toList());

        assert persons != null : "Ensure that persons found is not null (can still be empty)";

        model.setPersonsTeamToNone(persons);
        model.deleteTeam(targetTeam);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, teamName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteTeamCommand)) {
            return false;
        }
        DeleteTeamCommand otherCmd = (DeleteTeamCommand) other;
        return teamName.equals(otherCmd.teamName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("teamName", teamName)
                .toString();
    }
}
