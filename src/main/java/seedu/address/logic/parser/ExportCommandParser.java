package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

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
        String trimmedArgs = args.trim();

        String userHome = System.getProperty("user.home");
        Path downloadsPath = Paths.get(userHome, "Downloads");
        // Case 1: no arguments — use default export path
        if (trimmedArgs.isEmpty()) {
            return new ExportCommand(downloadsPath.toString());
        }

        // Case 2: prefixed argument (must use file/)
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILE);
        Optional<String> filePath = argMultimap.getValue(PREFIX_FILE);

        if (!filePath.isPresent() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FILE);
        return new ExportCommand(filePath.get());
    }

    /*
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
    */
}
