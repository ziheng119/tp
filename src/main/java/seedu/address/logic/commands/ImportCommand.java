package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.JsonAddressBookStorage;

/**
 * Imports an address book JSON file into the system.
 */
public class ImportCommand extends Command {

    private static final Logger logger = LogsCenter.getLogger(ImportCommand.class);

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports an address book JSON file. "
            + "Parameters: " + PREFIX_FILE + "FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILE + "my_import.json";

    public static final String MESSAGE_SUCCESS = "Address book successfully imported from: %1$s";
    public static final String MESSAGE_FOUND_FAILURE = "The specified file does not exist or is a directory: %1$s";
    public static final String MESSAGE_IO_FAILURE = "I/O error occurred while importing the file: %1$s";
    public static final String MESSAGE_IO_TARGET_FAILURE = "I/O error occurred while preparing target file: %1$s";
    public static final String MESSAGE_PATH_FAILURE = "The specified file path is invalid: %1$s";
    public static final String MESSAGE_LOADING_FAILURE = "Failed to read the address book from the file: %1$s";

    private final String filePath;

    /**
     * Creates an ImportCommand to import data from the specified file path.
     */
    public ImportCommand(String filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    private Path prepareFilePath() throws CommandException {
        Path importPath = Paths.get(filePath);
        if (!importPath.isAbsolute()) {
            importPath = importPath.toAbsolutePath();
        }
        // Check if source file exists
        if (!Files.exists(importPath) || Files.isDirectory(importPath)) {
            logger.warning("Import failed: file not found or is directory at " + importPath);
            throw new CommandException(String.format(MESSAGE_FOUND_FAILURE, importPath));
        }
        return importPath;
    }

    private Path prepareTargetPath(Model model) throws CommandException {
        try {
            Path targetPath = model.getAddressBookFilePath();
            if (targetPath.getParent() != null) {
                Files.createDirectories(targetPath.getParent());
            }
            return targetPath;
        } catch (IOException e) {
            logger.warning("Failed to prepare target directory: " + e.getMessage());
            throw new CommandException(String.format(MESSAGE_IO_TARGET_FAILURE, filePath));
        }
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getAddressBookFilePath() == null) {
            logger.severe("Model has null address book file path.");
            throw new CommandException("Internal error: model does not have a valid address book file path.");
        }
        Path importPath;
        Path targetPath;
        try {
            importPath = prepareFilePath();
            targetPath = prepareTargetPath(model);

            // Copy the file into data/addressbook.json
            Files.copy(importPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            JsonAddressBookStorage storage = new JsonAddressBookStorage(targetPath);
            Optional<ReadOnlyAddressBook> optionalNewData = storage.readAddressBook();
            ReadOnlyAddressBook newData = optionalNewData.orElseThrow(() -> {
                logger.warning("Failed to load address book data from file: " + filePath);
                return new CommandException(String.format(MESSAGE_LOADING_FAILURE, filePath));
            });

            // Update the model
            model.setAddressBook(newData);
            return new CommandResult(String.format(MESSAGE_SUCCESS, importPath));

        } catch (IOException e) {
            logger.warning("I/O exception during import: " + e.getMessage());
            throw new CommandException(String.format(MESSAGE_IO_FAILURE, filePath));
        } catch (InvalidPathException e) {
            logger.warning("Data loading exception: " + e.getMessage());
            throw new CommandException(String.format(MESSAGE_PATH_FAILURE, filePath));
        } catch (DataLoadingException e) {
            logger.severe("Unexpected exception: " + e.getMessage());
            throw new CommandException(String.format(MESSAGE_LOADING_FAILURE, filePath));
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
