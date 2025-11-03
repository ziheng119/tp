package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.team.Team;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
            new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), Collections.emptyList()));
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t Bob \t", expectedFindCommand);
    }

    @Test
    public void parse_invalidTeamName_throwsParseException() {
        String invalidTeamName = "dog";
        String expectedMessage = String.format("The following team name(s) are not valid: %s.%n%s",
            invalidTeamName, Team.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " t/dog", expectedMessage);
    }

    @Test
    public void parse_validTeamName_returnsFindCommand() {
        FindCommand expectedFindCommand =
            new FindCommand(new NameContainsKeywordsPredicate(Collections.emptyList(), Arrays.asList("F12-3")));
        assertParseSuccess(parser, " t/F12-3", expectedFindCommand);
    }

}
