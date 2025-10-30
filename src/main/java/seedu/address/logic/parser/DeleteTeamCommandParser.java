package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import seedu.address.logic.commands.DeleteTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeleteTeamCommand} object
 */
public class DeleteTeamCommandParser implements Parser<DeleteTeamCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteTeamCommand}
     * and returns a {@code DeleteTeamCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTeamCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TEAM);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TEAM);

        String teamName;

        boolean isInvalidCommandFormat = !argMultimap.getValue(PREFIX_TEAM).isPresent()
                || !argMultimap.getPreamble().isEmpty();

        if (isInvalidCommandFormat) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTeamCommand.MESSAGE_USAGE));
        }

        // Extract team name from team prefix
        teamName = ParserUtil.parseTeamName(argMultimap.getValue(PREFIX_TEAM).get());

        return new DeleteTeamCommand(teamName);
    }
}
