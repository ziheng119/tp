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
    public void execute_duplicateEmail_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        Person personWithSameEmail = new PersonBuilder()
                .withName("Different Name")
                .withEmail(personInList.getEmail().value)
                .withPhone("99999999")
                .withGithub("differentuser")
                .build();
        assertCommandFailure(new AddStudentCommand(personWithSameEmail), model,
                AddStudentCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePhone_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        Person personWithSamePhone = new PersonBuilder()
                .withName("Different Name")
                .withEmail("different@example.com")
                .withPhone(personInList.getPhone().value)
                .withGithub("differentuser")
                .build();
        assertCommandFailure(new AddStudentCommand(personWithSamePhone), model,
                AddStudentCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateGithub_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        Person personWithSameGithub = new PersonBuilder()
                .withName("Different Name")
                .withEmail("different@example.com")
                .withPhone("99999999")
                .withGithub(personInList.getGithub().value)
                .build();
        assertCommandFailure(new AddStudentCommand(personWithSameGithub), model,
                AddStudentCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
