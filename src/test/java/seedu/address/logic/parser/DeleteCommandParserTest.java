package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.Email;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validEmail_returnsDeleteCommand() {
        Email email = new Email("alice@example.com");
        assertParseSuccess(parser, " e/alice@example.com", new DeleteCommand(email));
    }

    @Test
    public void parse_invalidEmail_throwsParseException() {
        assertParseFailure(parser, " e/invalidemail",
                DeleteCommandParser.INVALID_EMAIL_FORMAT);
    }

    @Test
    public void parse_emptyEmail_throwsParseException() {
        assertParseFailure(parser, " e/",
                DeleteCommandParser.INVALID_EMAIL_FORMAT);
    }

    @Test
    public void parse_emailWithSpacesOnly_throwsParseException() {
        assertParseFailure(parser, " e/   ",
                DeleteCommandParser.INVALID_EMAIL_FORMAT);
    }

    @Test
    public void parse_emailWithPreamble_throwsParseException() {
        assertParseFailure(parser, "1 e/alice@example.com",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
