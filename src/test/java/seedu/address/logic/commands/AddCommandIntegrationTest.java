package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddStudentCommand(validPerson), model,
                String.format(AddStudentCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddStudentCommand(personInList), model,
                AddStudentCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateEmailCaseInsensitive_throwsCommandException() {
        // Add a person with uppercase email
        Person personWithUppercaseEmail = new PersonBuilder()
                .withName("Cavan Lee")
                .withEmail("CAVAN@GMAIL.COM")
                .withPhone("12345678")
                .withGithub("cavanlee")
                .build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(personWithUppercaseEmail);

        // Add the person with uppercase email - should succeed
        assertCommandSuccess(new AddStudentCommand(personWithUppercaseEmail), model,
                String.format(AddStudentCommand.MESSAGE_SUCCESS, Messages.format(personWithUppercaseEmail)),
                expectedModel);

        // Try to add another person with the same email but in lowercase - should fail
        // because emails are normalized to lowercase and treated as duplicates
        Person personWithLowercaseEmail = new PersonBuilder()
                .withName("Different Name")
                .withEmail("cavan@gmail.com")
                .withPhone("87654321")
                .withGithub("differentuser")
                .build();

        assertCommandFailure(new AddStudentCommand(personWithLowercaseEmail), model,
                AddStudentCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
