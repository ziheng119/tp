package seedu.address.logic.commands.util;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Shared helpers for team-related commands to reduce duplication and improve clarity.
 */
public final class TeamCommandUtil {

    private TeamCommandUtil() {}

    /** Resolves a person by index from the current filtered list or throws if the index is invalid. */
    public static Person getTargetPerson(Model model, Index index) throws CommandException {
        requireNonNull(model);
        requireNonNull(index);
        List<Person> visible = model.getFilteredPersonList();
        if (index.getZeroBased() >= visible.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return visible.get(index.getZeroBased());
    }

    /** Returns the team with the given name or throws if it does not exist. */
    public static Team validateTeamExists(Model model, String teamName) throws CommandException {
        requireNonNull(model);
        requireNonNull(teamName);
        Team team = model.getTeamByName(teamName);
        if (team == null) {
            // reuse per-command formatting by returning null message to caller? Keep simple here
            throw new CommandException(String.format("Team with name '%s' not found", teamName));
        }
        return team;
    }

    /** Ensures the person is a member of the team, otherwise throws with the provided message template. */
    public static void validatePersonMembership(Team team, Person person, String messageTemplate)
            throws CommandException {
        requireNonNull(team);
        requireNonNull(person);
        requireNonNull(messageTemplate);
        if (!team.hasPerson(person)) {
            throw new CommandException(String.format(messageTemplate, Messages.format(person), team.getName()));
        }
    }

    /** Ensures the person is NOT a member of the team, otherwise throws with the provided message template. */
    public static void validateNotMember(Team team, Person person, String messageTemplate) throws CommandException {
        requireNonNull(team);
        requireNonNull(person);
        requireNonNull(messageTemplate);
        if (team.hasPerson(person)) {
            throw new CommandException(String.format(messageTemplate, Messages.format(person), team.getName()));
        }
    }
}


