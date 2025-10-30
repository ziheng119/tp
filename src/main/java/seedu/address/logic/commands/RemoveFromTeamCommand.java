package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Removes a person from a team.
 */
public class RemoveFromTeamCommand extends Command {

    public static final String COMMAND_WORD = "remove_from_team";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a person from a team. "
            + "Parameters: INDEX(one-based positive integer) t/TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 t/Team1";

    public static final String MESSAGE_SUCCESS = "Person %s removed from team %s";
    public static final String MESSAGE_TEAM_NOT_FOUND = "Team with name '%s' not found";
    public static final String MESSAGE_PERSON_NOT_IN_TEAM = "Person %s is not in team %s";

    private final Index personIndex;
    private final String teamName;

    /**
     * Creates a RemoveFromTeamCommand to remove the specified person from the team.
     */
    public RemoveFromTeamCommand(Index personIndex, String teamName) {
        requireNonNull(personIndex);
        requireNonNull(teamName);
        this.personIndex = personIndex;
        this.teamName = teamName;
    }

    public Index getPersonIndex() {
        return personIndex;
    }

    public String getTeamName() {
        return teamName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person targetPerson = lastShownList.get(personIndex.getZeroBased());

        // Find the team by name
        Team targetTeam = model.getTeamByName(teamName);
        if (targetTeam == null) {
            throw new CommandException(String.format(MESSAGE_TEAM_NOT_FOUND, teamName));
        }

        // Check if person is in the team
        if (!targetTeam.hasPerson(targetPerson)) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_IN_TEAM,
                Messages.format(targetPerson), teamName));
        }

        // Remove person from team
        model.removePersonFromTeam(targetPerson, targetTeam);

        // Update the person's team to NONE by finding them by email
        Person updatedPerson = new Person(
                targetPerson.getName(),
                targetPerson.getPhone(),
                targetPerson.getEmail(),
                targetPerson.getGithub(),
                Team.NONE);

        model.setPerson(targetPerson, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                Messages.format(targetPerson), teamName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveFromTeamCommand)) {
            return false;
        }

        RemoveFromTeamCommand otherRemoveFromTeamCommand = (RemoveFromTeamCommand) other;
        return personIndex.equals(otherRemoveFromTeamCommand.personIndex)
                && teamName.equals(otherRemoveFromTeamCommand.teamName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personIndex", personIndex)
                .add("teamName", teamName)
                .toString();
    }
}
