package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

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
        assertThrows(ParseException.class, () -> parser.parse("John Doe Team1"));
    }

    @Test
    public void parse_missingPersonName_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("/team Team1"));
    }

    @Test
    public void parse_validInput_returnsAddStudentToTeamCommand() throws ParseException {
        AddStudentToTeamCommand expected = new AddStudentToTeamCommand("John Doe", "Team1");
        AddStudentToTeamCommand actual = parser.parse("John Doe /team Team1");
        assertEquals(expected.getStudentName(), actual.getStudentName());
        assertEquals(expected.getTeamName(), actual.getTeamName());
    }

    @Test
    public void parse_personNameWithSpaces_returnsAddStudentToTeamCommand() throws ParseException {
        AddStudentToTeamCommand expected = new AddStudentToTeamCommand("John Michael Doe", "Team1");
        AddStudentToTeamCommand actual = parser.parse("John Michael Doe /team Team1");
        assertEquals(expected.getStudentName(), actual.getStudentName());
        assertEquals(expected.getTeamName(), actual.getTeamName());
    }

    @Test
    public void parse_teamNameWithSpaces_returnsAddStudentToTeamCommand() throws ParseException {
        AddStudentToTeamCommand expected = new AddStudentToTeamCommand("John Doe", "Team Alpha");
        AddStudentToTeamCommand actual = parser.parse("John Doe /team Team Alpha");
        assertEquals(expected.getStudentName(), actual.getStudentName());
        assertEquals(expected.getTeamName(), actual.getTeamName());
    }

    @Test
    public void parse_extraWhitespace_returnsAddStudentToTeamCommand() throws ParseException {
        AddStudentToTeamCommand expected = new AddStudentToTeamCommand("John Doe", "Team1");
        AddStudentToTeamCommand actual = parser.parse("  John Doe  /team  Team1  ");
        assertEquals(expected.getStudentName(), actual.getStudentName());
        assertEquals(expected.getTeamName(), actual.getTeamName());
    }

}
