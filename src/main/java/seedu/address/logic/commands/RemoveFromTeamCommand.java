package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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
            + "Parameters: PERSON_NAME /team TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " John Doe /team Team_1";

    public static final String MESSAGE_SUCCESS = "Person %s removed from team %s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person with name '%s' not found in address book";
    public static final String MESSAGE_TEAM_NOT_FOUND = "Team with name '%s' not found";
    public static final String MESSAGE_PERSON_NOT_IN_TEAM = "Person %s is not in team %s";

    private final String personName;
    private final String teamName;

    /**
     * Creates a RemoveFromTeamCommand to remove the specified person from the team.
     */
    public RemoveFromTeamCommand(String personName, String teamName) {
        requireNonNull(personName);
        requireNonNull(teamName);
        this.personName = personName;
        this.teamName = teamName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Find the person by name
        Person targetPerson = findPersonByName(model, personName);
        if (targetPerson == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, personName));
        }

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
        return new CommandResult(String.format(MESSAGE_SUCCESS,
            Messages.format(targetPerson), teamName));
    }

    /**
     * Finds a person by name in the model.
     * Returns null if not found.
     */
    private Person findPersonByName(Model model, String name) {
        return model.getFilteredPersonList().stream()
                .filter(person -> person.getName().toString().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
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
        return personName.equals(otherRemoveFromTeamCommand.personName)
                && teamName.equals(otherRemoveFromTeamCommand.teamName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personName", personName)
                .add("teamName", teamName)
                .toString();
    }
}
