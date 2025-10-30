package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import seedu.address.commons.core.index.Index;
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
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TEAM);

        Index studentIndex;
        String teamName;

        // Extract person index from preamble (everything before the team prefix)
        try {
            studentIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(AddStudentToTeamCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_TEAM).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentToTeamCommand.MESSAGE_USAGE));
        }

        teamName = ParserUtil.parseTeamName(argMultimap.getValue(PREFIX_TEAM).get());

        return new AddStudentToTeamCommand(studentIndex, teamName);
    }
}
