package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    static final String INVALID_EMAIL_FORMAT = "Invalid email format. Please use: delete e/username@example.com";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EMAIL);

        // Check if deleting by email
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            // Ensure no preamble when using email prefix
            if (!argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }

            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EMAIL);

            String emailString = argMultimap.getValue(PREFIX_EMAIL).get().trim();
            if (emailString.isEmpty()) {
                throw new ParseException(INVALID_EMAIL_FORMAT);
            }

            try {
                Email email = ParserUtil.parseEmail(emailString);
                return new DeleteCommand(email);
            } catch (ParseException pe) {
                throw new ParseException(INVALID_EMAIL_FORMAT, pe);
            }
        }

        // Otherwise, delete by index
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteCommand(index);
    }

}
