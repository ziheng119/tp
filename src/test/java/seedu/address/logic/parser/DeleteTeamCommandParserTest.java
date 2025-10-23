package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteTeamCommand;
import seedu.address.model.team.Team;

public class DeleteTeamCommandParserTest {

    private final DeleteTeamCommandParser parser = new DeleteTeamCommandParser();

    @Test
    public void parse_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTeamCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "",
                expectedMessage);
        assertParseFailure(parser, " ",
                expectedMessage);
        assertParseFailure(parser, " F12-3",
                expectedMessage);
    }

    @Test
    public void parse_invalidTeam_throwsParseException() {
        assertParseFailure(parser, " t/Team 1",
                Team.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " t/",
                Team.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validTeamName_success() throws Exception {
        assertParseSuccess(parser, " t/F12-3", new DeleteTeamCommand("F12-3"));
    }
}
