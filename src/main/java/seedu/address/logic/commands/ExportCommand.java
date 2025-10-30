package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports the address book data to a specified JSON file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the current address book data to a JSON file. "
            + "Parameters: "
            + PREFIX_FILE + "FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILE + "my_export.json";

    public static final String MESSAGE_SUCCESS = "Address book successfully exported to: %1$s";
    public static final String MESSAGE_IO_FAILURE = "Failed to export data: %1$s";
    public static final String MESSAGE_PATH_FAILURE = "Failed to find path: %1$s";

    public static final String DEFAULT_FILE = "exported_addressbook.json";

    private static final Logger logger = LogsCenter.getLogger(ExportCommand.class);
    private final String filePath;

    /**
     * Creates an ExportCommand to export data to the specified file path.
     */
    public ExportCommand(String filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    private Path preparePath() throws CommandException {
        Path exportPath = Paths.get(filePath);
        if (!exportPath.isAbsolute()) {
            exportPath = Paths.get("data").resolve(exportPath);
            exportPath = exportPath.toAbsolutePath();
        }
        // Ensure parent directories exist
        if (exportPath.getParent() != null) {
            try {
                Files.createDirectories(exportPath.getParent());
            } catch (IOException e) {
                throw new CommandException(String.format(MESSAGE_IO_FAILURE, exportPath));
            }
        }

        // Add DEFAULT_FILE or .json if required
        if (Files.isDirectory(exportPath)) {
            exportPath = exportPath.resolve(DEFAULT_FILE);
        } else if (!exportPath.toString().toLowerCase().endsWith(".json")) {
            exportPath = Paths.get(exportPath + ".json");
        }
        return exportPath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Path exportPath = null;
        try {
            exportPath = preparePath();
            // Write JSON data
            Files.copy(model.getAddressBookFilePath(), exportPath,
                    StandardCopyOption.REPLACE_EXISTING);
            return new CommandResult(String.format(MESSAGE_SUCCESS, exportPath));

        } catch (IOException e) {
            logger.warning("I/O exception during export: " + e.getMessage());
            throw new CommandException(String.format(MESSAGE_IO_FAILURE, exportPath));
        } catch (InvalidPathException e) {
            logger.warning("Invalid path specified for export: " + e.getMessage());
            throw new CommandException(String.format(MESSAGE_PATH_FAILURE, filePath));
        } catch (Exception e) {
            // This should not happen
            logger.warning("Unexpected error during export command: " + e.getMessage());
            throw new CommandException("Unexpected error occurred during export.");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof ExportCommand)) {
            return false;
        }

        if (other == this) {
            return true;
        }
        ExportCommand otherCommand = (ExportCommand) other;
        return filePath.equals(otherCommand.filePath);
    }

    @Override
    public String toString() {
        return "ExportCommand{"
                + "filePath='" + filePath + '\''
                + '}';
    }
}
