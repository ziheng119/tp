package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ Storage methods ==============================
    /**
     * Creates a timestamped backup of the current address book file to prevent data loss
     * in case the file is corrupted.
     * <p>
     * The backup file is created in the same directory as the original address book,
     * with a filename format of:
     * <pre>
     * originalFileName_corrupted_yyyy-MM-ddTHH-mm-ss.json
     * </pre>
     * Colons in the timestamp are replaced with hyphens to ensure the filename is valid.
     * <p>
     * If an I/O error occurs during the backup process, a severe log message is recorded,
     * but the method does not throw an exception.
     */
    public void backupFile() {
        try {
            Path corruptedFile = getAddressBookFilePath();
            // Create a timestamped backup name inside the same folder
            String timestamp = java.time.LocalDateTime.now()
                                .toString()
                                .replace(":", "-"); // avoid invalid filename chars
            String backupFilename = corruptedFile.getFileName().toString().replace(".json", "");
            Path backupFile = corruptedFile.resolveSibling(
                        backupFilename + "_corrupted_" + timestamp + ".json");
            Files.copy(corruptedFile, backupFile);
        } catch (IOException ioEx) {
            logger.severe("Failed to back up corrupted data file: " + ioEx.getMessage());
        }
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

}
