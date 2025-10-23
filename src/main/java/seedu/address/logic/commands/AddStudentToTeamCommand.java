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
import seedu.address.model.team.exceptions.TeamMaxCapacityException;

/**
 * Adds a student (person) to a team.
 */
public class AddStudentToTeamCommand extends Command {

    public static final String COMMAND_WORD = "add_to_team";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to a team. "
            + "Parameters: INDEX t/TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 t/Team1";

    public static final String MESSAGE_SUCCESS = "Person %s added to team %s";
    public static final String MESSAGE_TEAM_NOT_FOUND = "Team with name '%s' not found";
    public static final String MESSAGE_PERSON_ALREADY_IN_TEAM = "Person %s is already in team %s";
    public static final String MESSAGE_PERSON_IN_ANOTHER_TEAM = "Person %s is already in team %s. "
            + "Remove them from their current team before adding to a new team";
    public static final String MESSAGE_TEAM_FULL = "Team %s is at maximum capacity (5 members)";

    private final Index studentIndex;
    private final String teamName;

    /**
     * Creates an AddStudentToTeamCommand to add the specified person to the team.
     */
    public AddStudentToTeamCommand(Index studentIndex, String teamName) {
        requireNonNull(studentIndex);
        requireNonNull(teamName);
        this.studentIndex = studentIndex;
        this.teamName = teamName;
    }

    public Index getStudentIndex() {
        return studentIndex;
    }

    public String getTeamName() {
        return teamName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (studentIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person targetPerson = lastShownList.get(studentIndex.getZeroBased());

        // Find the team by name
        Team targetTeam = model.getTeamByName(teamName);
        if (targetTeam == null) {
            throw new CommandException(String.format(MESSAGE_TEAM_NOT_FOUND, teamName));
        }

        // Check if person is already in any team
        Team existingTeam = model.getTeamContainingPerson(targetPerson);
        if (existingTeam != null) {
            if (existingTeam.equals(targetTeam)) {
                // Person is already in the same team
                throw new CommandException(String.format(MESSAGE_PERSON_ALREADY_IN_TEAM,
                    Messages.format(targetPerson), teamName));
            } else {
                // Person is in a different team
                throw new CommandException(String.format(MESSAGE_PERSON_IN_ANOTHER_TEAM,
                    Messages.format(targetPerson), existingTeam.getName()));
            }
        }

        try {
            Person updatedPerson = new Person(
                    targetPerson.getName(),
                    targetPerson.getPhone(),
                    targetPerson.getEmail(),
                    targetPerson.getGithub(),
                    targetTeam);

            model.setPerson(targetPerson, updatedPerson);
            model.addPersonToTeam(updatedPerson, targetTeam);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    Messages.format(targetPerson), teamName));
        } catch (TeamMaxCapacityException e) {
            throw new CommandException(String.format(MESSAGE_TEAM_FULL, teamName));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddStudentToTeamCommand)) {
            return false;
        }

        AddStudentToTeamCommand otherAddStudentToTeamCommand = (AddStudentToTeamCommand) other;
        return studentIndex.equals(otherAddStudentToTeamCommand.studentIndex)
                && teamName.equals(otherAddStudentToTeamCommand.teamName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentIndex", studentIndex)
                .add("teamName", teamName)
                .toString();
    }
}
