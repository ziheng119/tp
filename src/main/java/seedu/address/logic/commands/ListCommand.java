package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.team.Team.NONE;

import java.util.stream.Collectors;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        String teamsList = model.getAddressBook().getTeamList().stream()
                .filter(team -> !team.equals(NONE))
                .map(team -> team.getName())
                .collect(Collectors.joining(", "));
        String message = MESSAGE_SUCCESS;
        if (!teamsList.isEmpty()) {
            message += "\nTeams created so far: " + teamsList;
        }
        return new CommandResult(message);
    }
}
