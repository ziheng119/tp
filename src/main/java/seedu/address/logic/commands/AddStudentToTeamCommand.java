package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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
            + "Parameters: PERSON_NAME /team TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " John Doe /team Team_1";

    public static final String MESSAGE_SUCCESS = "Person %s added to team %s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person with name '%s' not found in address book";
    public static final String MESSAGE_TEAM_NOT_FOUND = "Team with name '%s' not found";
    public static final String MESSAGE_PERSON_ALREADY_IN_TEAM = "Person %s is already in team %s";
    public static final String MESSAGE_PERSON_IN_ANOTHER_TEAM = "Person %s is already in team %s. "
            + "Remove them from their current team before adding to a new team";
    public static final String MESSAGE_TEAM_FULL = "Team %s is at maximum capacity (5 members)";

    private final String studentName;
    private final String teamName;

    /**
     * Creates an AddStudentToTeamCommand to add the specified person to the team.
     */
    public AddStudentToTeamCommand(String studentName, String teamName) {
        requireNonNull(studentName);
        requireNonNull(teamName);
        this.studentName = studentName;
        this.teamName = teamName;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getTeamName() {
        return teamName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Find the person by name
        Person targetPerson = findPersonByName(model, studentName);
        if (targetPerson == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, studentName));
        }

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
            // Add person to team
            model.addPersonToTeam(targetPerson, targetTeam);
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                Messages.format(targetPerson), teamName));
        } catch (TeamMaxCapacityException e) {
            throw new CommandException(String.format(MESSAGE_TEAM_FULL, teamName));
        }
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
        if (!(other instanceof AddStudentToTeamCommand)) {
            return false;
        }

        AddStudentToTeamCommand otherAddStudentToTeamCommand = (AddStudentToTeamCommand) other;
        return studentName.equals(otherAddStudentToTeamCommand.studentName)
                && teamName.equals(otherAddStudentToTeamCommand.teamName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentName", studentName)
                .add("teamName", teamName)
                .toString();
    }
}
