package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddStudentToTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddStudentToTeamCommandParserTest {

    private final AddStudentToTeamCommandParser parser = new AddStudentToTeamCommandParser();

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
        assertThrows(ParseException.class, () -> parser.parse("1 Team1"));
    }

    @Test
    public void parse_missingPersonIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("t/ Team1"));
    }

    @Test
    public void parse_validInput_returnsAddStudentToTeamCommand() throws ParseException {
        AddStudentToTeamCommand expected = new AddStudentToTeamCommand(Index.fromOneBased(1), "Team1");
        AddStudentToTeamCommand actual = parser.parse("1 t/Team1");
        assertEquals(expected.getStudentIndex(), actual.getStudentIndex());
        assertEquals(expected.getTeamName(), actual.getTeamName());
    }

    @Test
    public void parse_multipleDigitIndex_returnsAddStudentToTeamCommand() throws ParseException {
        AddStudentToTeamCommand expected = new AddStudentToTeamCommand(Index.fromOneBased(123), "Team1");
        AddStudentToTeamCommand actual = parser.parse("123 t/Team1");
        assertEquals(expected.getStudentIndex(), actual.getStudentIndex());
        assertEquals(expected.getTeamName(), actual.getTeamName());
    }

    @Test
    public void parse_teamNameWithSpaces_returnsAddStudentToTeamCommand() throws ParseException {
        AddStudentToTeamCommand expected = new AddStudentToTeamCommand(Index.fromOneBased(1), "Team Alpha");
        AddStudentToTeamCommand actual = parser.parse("1 t/Team Alpha");
        assertEquals(expected.getStudentIndex(), actual.getStudentIndex());
        assertEquals(expected.getTeamName(), actual.getTeamName());
    }

    @Test
    public void parse_extraWhitespace_returnsAddStudentToTeamCommand() throws ParseException {
        AddStudentToTeamCommand expected = new AddStudentToTeamCommand(Index.fromOneBased(1), "Team1");
        AddStudentToTeamCommand actual = parser.parse("  1  t/  Team1  ");
        assertEquals(expected.getStudentIndex(), actual.getStudentIndex());
        assertEquals(expected.getTeamName(), actual.getTeamName());
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("abc t/Team1"));
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("0 t/Team1"));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("-1 t/Team1"));
    }

    @Test
    public void parse_emptyTeamName_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("1 t/"));
    }

}
