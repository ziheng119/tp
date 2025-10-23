package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CreateTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class CreateTeamCommandParserTest {

    private final CreateTeamCommandParser parser = new CreateTeamCommandParser();

    @Test
    public void parse_validTeamName_success() throws Exception {
        String teamName = "F12-3";
        CreateTeamCommand command = parser.parse(" t/" + teamName);
        assertEquals(new CreateTeamCommand(teamName), command);
    }

    @Test
    public void parse_invalidTeamName_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" t/Team@123"));
        assertThrows(ParseException.class, () -> parser.parse(""));
        assertThrows(ParseException.class, () -> parser.parse("   "));
    }

    @Test
    public void parse_extraTextBeforePrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("extra t/Team1"));
        assertThrows(ParseException.class, () -> parser.parse("invalid t/F12-3"));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("Team1"));
        assertThrows(ParseException.class, () -> parser.parse("F12-3"));
    }
}
