package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Deletes a team and sets all members' team to Team.NONE.
 */
public class DeleteTeamCommand extends Command {

    public static final String COMMAND_WORD = "delete_team";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a team.\n"
            + "Parameters: t/TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " t/Team1";

    public static final String MESSAGE_SUCCESS = "Team %s deleted";
    public static final String MESSAGE_TEAM_NOT_FOUND = "Team with name '%s' not found";

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

        Team targetTeam = model.getTeamByName(teamName);
        if (targetTeam == null) {
            throw new CommandException(String.format(MESSAGE_TEAM_NOT_FOUND, teamName));
        }

        // collect current members (use logical equality on person.team)
        List<Person> members = model.getFilteredPersonList().stream()
                .filter(p -> p.getTeam().equals(targetTeam))
                .collect(Collectors.toList());

        // For each member, create new Person with Team.NONE and replace in model
        for (Person member : members) {
            Person updatedPerson = new Person(
                    member.getName(),
                    member.getPhone(),
                    member.getEmail(),
                    member.getGithub(),
                    Team.NONE);
            replacePersonByEmail(model, member.getEmail(), updatedPerson);
        }

        // remove team from model
        model.removeTeam(targetTeam);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, teamName));
    }

    /**
     * Replaces a person in the model by their email address.
     * This is needed because setPerson uses equals() which includes team field.
     */
    private void replacePersonByEmail(Model model, seedu.address.model.person.Email email, Person updatedPerson) {
        Person personToReplace = model.getFilteredPersonList().stream()
                .filter(person -> person.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (personToReplace != null) {
            model.setPerson(personToReplace, updatedPerson);
        }
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
