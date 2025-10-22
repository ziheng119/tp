package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        String teamName;
        teamName = argMultimap.getPreamble();

        String parsedTeamName = ParserUtil.parseTeamName(teamName);

        return new DeleteTeamCommand(parsedTeamName);
    }
}
