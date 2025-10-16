package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

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
    public void parse_missingTeamPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("John Doe Team1"));
    }

    @Test
    public void parse_missingPersonName_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("t/Team1"));
    }

    @Test
    public void parse_validInput_returnsRemoveFromTeamCommand() throws ParseException {
        RemoveFromTeamCommand expected = new RemoveFromTeamCommand("John Doe", "Team1");
        RemoveFromTeamCommand actual = parser.parse("John Doe t/Team1");
        assertEquals(expected.getPersonName(), actual.getPersonName());
        assertEquals(expected.getTeamName(), actual.getTeamName());
    }

    @Test
    public void parse_personNameWithSpaces_returnsRemoveFromTeamCommand() throws ParseException {
        RemoveFromTeamCommand expected = new RemoveFromTeamCommand("John Michael Doe", "Team1");
        RemoveFromTeamCommand actual = parser.parse("John Michael Doe t/Team1");
        assertEquals(expected.getPersonName(), actual.getPersonName());
        assertEquals(expected.getTeamName(), actual.getTeamName());
    }

    @Test
    public void parse_teamNameWithSpaces_returnsRemoveFromTeamCommand() throws ParseException {
        RemoveFromTeamCommand expected = new RemoveFromTeamCommand("John Doe", "Team Alpha");
        RemoveFromTeamCommand actual = parser.parse("John Doe t/Team Alpha");
        assertEquals(expected.getPersonName(), actual.getPersonName());
        assertEquals(expected.getTeamName(), actual.getTeamName());
    }

    @Test
    public void parse_extraWhitespace_returnsRemoveFromTeamCommand() throws ParseException {
        RemoveFromTeamCommand expected = new RemoveFromTeamCommand("John Doe", "Team1");
        RemoveFromTeamCommand actual = parser.parse("  John Doe  t/Team1  ");
        assertEquals(expected.getPersonName(), actual.getPersonName());
        assertEquals(expected.getTeamName(), actual.getTeamName());
    }

}
