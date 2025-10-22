package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteTeamCommandParserTest {

    private final DeleteTeamCommandParser parser = new DeleteTeamCommandParser();

    @Test
    public void parse_validTeamName_success() throws Exception {
        String teamName = "F12-3";
        DeleteTeamCommand command = parser.parse(teamName);
        assertEquals(new DeleteTeamCommand(teamName), command);
    }

    @Test
    public void parse_invalidTeamName_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("Team@123"));
        assertThrows(ParseException.class, () -> parser.parse(""));
        assertThrows(ParseException.class, () -> parser.parse(" "));
    }
}
