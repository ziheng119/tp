package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ImportCommandParserTest {

    private final ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validFilePath_success() throws Exception {
        String userInput = " " + PREFIX_FILE + "C:/Users/Test/Documents/sample.json";
        ImportCommand command = new ImportCommand("C:/Users/Test/Documents/sample.json");
        assertParseSuccess(parser, userInput, command);
    }

    @Test
    public void parse_pathWithSpaces_success() throws Exception {
        String userInput = " " + PREFIX_FILE + "C:/Users/Test/My Folder/sample.json";
        ImportCommand command = new ImportCommand("C:/Users/Test/My Folder/sample.json");
        assertParseSuccess(parser, userInput, command);
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
        assertThrows(ParseException.class, () -> parser.parse("   "));
    }

    @Test
    public void parse_nullArg_throwsParseException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
}
