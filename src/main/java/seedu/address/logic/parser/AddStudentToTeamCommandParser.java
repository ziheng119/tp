package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import seedu.address.logic.commands.AddStudentToTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code AddStudentToTeamCommand} object
 */
public class AddStudentToTeamCommandParser implements Parser<AddStudentToTeamCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddStudentToTeamCommand}
     * and returns an {@code AddStudentToTeamCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddStudentToTeamCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TEAM);

        String studentName;
        String teamName;

        // Extract person name from preamble (everything before the team prefix)
        studentName = argMultimap.getPreamble();

        // Extract team name from team prefix
        if (argMultimap.getValue(PREFIX_TEAM).isEmpty()) {
            throw new ParseException(String.format(AddStudentToTeamCommand.MESSAGE_USAGE));
        }
        teamName = argMultimap.getValue(PREFIX_TEAM).get();

        if (studentName.isEmpty()) {
            throw new ParseException(String.format(AddStudentToTeamCommand.MESSAGE_USAGE));
        }

        return new AddStudentToTeamCommand(studentName, teamName);
    }
}
