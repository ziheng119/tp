package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import seedu.address.logic.commands.CreateTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code CreateTeamCommand} object
 */
public class CreateTeamCommandParser implements Parser<CreateTeamCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code CreateTeamCommand}
     * and returns a {@code CreateTeamCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateTeamCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TEAM);

        if (!argMultimap.getValue(PREFIX_TEAM).isPresent() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateTeamCommand.MESSAGE_USAGE));
        }

        String teamName = argMultimap.getValue(PREFIX_TEAM).get();
        String parsedTeamName = ParserUtil.parseTeamName(teamName);

        return new CreateTeamCommand(parsedTeamName);
    }
}
