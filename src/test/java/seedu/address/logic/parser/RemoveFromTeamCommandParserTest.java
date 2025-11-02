package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveFromTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class RemoveFromTeamCommandParserTest {

    private final RemoveFromTeamCommandParser parser = new RemoveFromTeamCommandParser();

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
    public void parse_hasTeamFlag_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("1 t/F12-3"));
    }

    @Test
    public void parse_multipleDigitIndex_returnsRemoveFromTeamCommand() throws ParseException {
        RemoveFromTeamCommand expected = new RemoveFromTeamCommand(Index.fromOneBased(123));
        RemoveFromTeamCommand actual = parser.parse("123");
        assertEquals(expected.getPersonIndex(), actual.getPersonIndex());
    }

    @Test
    public void parse_extraWhitespace_returnsRemoveFromTeamCommand() throws ParseException {
        RemoveFromTeamCommand expected = new RemoveFromTeamCommand(Index.fromOneBased(1));
        RemoveFromTeamCommand actual = parser.parse("  1    ");
        assertEquals(expected.getPersonIndex(), actual.getPersonIndex());
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("abc"));
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("0"));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("-1"));
    }
}
