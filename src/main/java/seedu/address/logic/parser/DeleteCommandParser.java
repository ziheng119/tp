package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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
        String trimmedArgs = args.trim();

        // Check if deleting by email (starts with "e/")
        if (trimmedArgs.startsWith("e/")) {
            String emailString = trimmedArgs.substring(2).trim();
            try {
                Email email = ParserUtil.parseEmail(emailString);
                return new DeleteCommand(email);
            } catch (ParseException pe) {
                throw new ParseException(INVALID_EMAIL_FORMAT);
            }
        }

        // Otherwise, delete by index
        try {
            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
