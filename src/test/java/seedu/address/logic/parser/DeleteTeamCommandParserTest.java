package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteTeamCommandParserTest {

    private final DeleteTeamCommandParser parser = new DeleteTeamCommandParser();

    @Test
    public void parse_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_emptyString_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("   "));
    }

    @Test
    public void parse_missingTeamPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("F12-3"));
    }

    @Test
    public void parse_invalidTeam_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("t/TEAM 1"));
    }

    @Test
    public void parse_emptyTeamName_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("t/"));
    }

    @Test
    public void parse_validTeamName_success() throws Exception {
        assertParseSuccess(parser, " t/F12-3", new DeleteTeamCommand("F12-3"));
    }
}
