package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import java.util.Optional;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public ExportCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILE);
        Optional<String> filePath = argMultimap.getValue(PREFIX_FILE);

        boolean isPathEmpty = filePath.isEmpty();
        boolean isPreambleEmpty = argMultimap.getPreamble().isEmpty();
        if (isPathEmpty || !isPreambleEmpty) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FILE);
        String path = filePath.get().trim();
        if (!path.matches("^[a-zA-Z0-9](?:[a-zA-Z0-9 ._-]*[a-zA-Z0-9])?\\.json$")) {
            throw new ParseException(ExportCommand.MESSAGE_INVALID_FILENAME);
        }
        return new ExportCommand(path);
    }

}
