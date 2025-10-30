package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Unit tests for ImportCommand.
 */
public class ImportCommandTest {

    @TempDir
    public Path tempDir; // Temporary directory provided by JUnit

    private Path importFile;
    private Path destinationFile;
    private Model model;

    @BeforeEach
    public void setUp() throws IOException {
        // Create temporary JSON source file
        importFile = tempDir.resolve("test_import.json");
        Files.writeString(importFile, "{\"persons\":[]}", StandardOpenOption.CREATE);

        // Simulate the real data directory
        destinationFile = Path.of("data", "sweatless_storage.json");
        Files.createDirectories(destinationFile.getParent());

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validFilePath_success() throws Exception {
        ImportCommand command = new ImportCommand(importFile.toString());
        CommandResult result = command.execute(model);

        // Verify the import worked
        assertTrue(Files.exists(destinationFile));
        assertTrue(Files.size(destinationFile) > 0);
        assertTrue(result.getFeedbackToUser().contains("successfully imported"));
    }

    @Test
    public void execute_nonExistentFile_throwsCommandException() {
        ImportCommand command = new ImportCommand(tempDir.resolve("does_not_exist.json").toString());
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_invalidPath_throwsCommandException() {
        ImportCommand command = new ImportCommand("\0invalid.json");
        assertThrows(CommandException.class, () -> command.execute(model));
    }
}
