package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Imports an address book JSON file into the system.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports an address book JSON file. "
            + "Parameters: " + PREFIX_FILE + "FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILE + "my_import.json";

    public static final String MESSAGE_SUCCESS = "Address book successfully imported from: %1$s";
    public static final String MESSAGE_FAILURE = "Failed to import data: %1$s";

    private final String filePath;

    /**
     * Creates an ImportCommand to import data from the specified file path.
     */
    public ImportCommand(String filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            Path importPath = Paths.get(filePath);
            if (!importPath.isAbsolute()) {
                importPath = importPath.toAbsolutePath();
            }
            // Check if source file exists
            if (!Files.exists(importPath) || Files.isDirectory(importPath)) {
                throw new CommandException("The specified file does not exist or is a directory: " + importPath);
            }

            // Ensure parent directories exist for the target
            Path targetPath = model.getAddressBookFilePath();
            if (targetPath.getParent() != null) {
                Files.createDirectories(targetPath.getParent());
            }

            // Copy the file into data/addressbook.json
            Files.copy(importPath, targetPath, StandardCopyOption.REPLACE_EXISTING);

            return new CommandResult(String.format(MESSAGE_SUCCESS, importPath));

        } catch (IOException | InvalidPathException e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ImportCommand)) {
            return false;
        }

        ImportCommand otherCommand = (ImportCommand) other;
        return filePath.equals(otherCommand.filePath);
    }
}
