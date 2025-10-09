package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using their displayed index or email from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number or email.\n"
            + "Parameters: INDEX (must be a positive integer) OR e/EMAIL\n"
            + "Examples:\n"
            + COMMAND_WORD + " 1\n"
            + COMMAND_WORD + " e/johndoe@example.com";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No person found with email: %1$s";

    private final Index targetIndex;
    private final Email targetEmail;

    /**
     * Creates a DeleteCommand to delete the person at the specified {@code targetIndex}.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetEmail = null;
    }

    /**
     * Creates a DeleteCommand to delete the person with the specified {@code targetEmail}.
     */
    public DeleteCommand(Email targetEmail) {
        this.targetIndex = null;
        this.targetEmail = targetEmail;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToDelete;

        if (targetIndex != null) {
            // Delete by index
            List<Person> lastShownList = model.getFilteredPersonList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            personToDelete = lastShownList.get(targetIndex.getZeroBased());
        } else {
            // Delete by email (case-insensitive)
            List<Person> allPersons = model.getFilteredPersonList();
            personToDelete = null;

            for (Person person : allPersons) {
                if (person.getEmail().value.equalsIgnoreCase(targetEmail.value)) {
                    personToDelete = person;
                    break;
                }
            }

            if (personToDelete == null) {
                throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, targetEmail));
            }
        }

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;

        // Compare targetIndex (handle nulls)
        boolean indexEquals = (targetIndex == null && otherDeleteCommand.targetIndex == null)
                || (targetIndex != null && targetIndex.equals(otherDeleteCommand.targetIndex));

        // Compare targetEmail (handle nulls)
        boolean emailEquals = (targetEmail == null && otherDeleteCommand.targetEmail == null)
                || (targetEmail != null && targetEmail.equals(otherDeleteCommand.targetEmail));

        return indexEquals && emailEquals;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("targetEmail", targetEmail)
                .toString();
    }
}
