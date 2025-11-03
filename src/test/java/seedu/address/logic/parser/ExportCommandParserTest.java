package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = " " + PREFIX_FILE + "export.json";
        ExportCommand expectedCommand = new ExportCommand("export.json");
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidFormat_failure() {
        // Invalid: path without f/ prefix
        String userInput = "C:\\Users\\User\\Documents\\export.json";
        String expectedMessage = String.format(seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_multiplePrefixes_failure() {
        // Multiple f/ prefixes should fail
        String userInput = " " + PREFIX_FILE + "C:\\file1.json " + PREFIX_FILE + "C:\\file2.json";
        String expectedMessage = Messages.MESSAGE_DUPLICATE_FIELDS + PREFIX_FILE;
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
