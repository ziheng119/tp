package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.team.Team;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TEAM);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TEAM);

        List<String> nameKeywords = argMultimap.getAllValues(PREFIX_NAME).stream()
                .flatMap(s -> Arrays.stream(s.trim().split("\\s+")))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        List<String> teamKeywords = argMultimap.getAllValues(PREFIX_TEAM).stream()
                .flatMap(s -> Arrays.stream(s.trim().split("\\s+")))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        for (String teamKeyword : teamKeywords) {
            if (!Team.isValidName(teamKeyword)) {
                throw new ParseException(String.format("%s is not a valid team name. %s",
                        teamKeyword, Team.MESSAGE_CONSTRAINTS));
            }
        }

        if (nameKeywords.isEmpty() && teamKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new NameContainsKeywordsPredicate(nameKeywords, teamKeywords));
    }

}
