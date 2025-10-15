package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Jackson-friendly version of {@link Team}.
 */
class JsonAdaptedTeam {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Team's Name field is missing!";
    public static final String MISSING_PERSON_MESSAGE_FORMAT = "Member %s not found in address book!";

    private final String name;
    private final List<Email> members = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTeam} with the given team details.
     */
    @JsonCreator
    public JsonAdaptedTeam(@JsonProperty("name") String name,
                @JsonProperty("members") List<Email> members) {
        this.name = name;
        if (members != null) {
            this.members.addAll(members);
        }
    }

    /**
     * Converts a given {@code Team} into this class for Jackson use.
     */
    public JsonAdaptedTeam(Team source) {
        name = source.getName();
        members.addAll(source.getPersonList().stream()
                .map(Person::getEmail)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted team object into the model's {@code Team} object.
     * Needs all persons to match email and link correct person object
     */
    public Team toModelType(ObservableList<Person> allPersons) throws IllegalValueException {
        List<Person> memberList = new ArrayList<>();
        for (Email email : members) {
            Person found = allPersons.stream()
                            .filter(person -> person.getEmail().equals(email))
                            .findFirst()
                            .orElse(null);
            if (found == null) {
                throw new IllegalValueException(String.format(MISSING_PERSON_MESSAGE_FORMAT, email));
            }
            memberList.add(found);
        }
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }

        if (name == "") {
            return Team.NONE;
        }

        if (!Team.isValidName(name)) {
            throw new IllegalValueException(Team.MESSAGE_CONSTRAINTS);
        }

        return new Team(name, FXCollections.observableList(memberList));
    }
}
