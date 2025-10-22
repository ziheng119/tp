package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Jackson-friendly version of {@link Team}.
 */
class JsonAdaptedTeam {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Team's Name field is missing!";

    private final String name;
    private final List<Email> members = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTeam} with the given team details.
     */
    @JsonCreator
    public JsonAdaptedTeam(@JsonProperty("name") String name,
                @JsonProperty("members") List<Email> members) {
        assert name != null : "Ensure name is not null";
        this.name = name;
        if (members != null) {
            assert !members.contains(null) : "Member email list should not contain null values";
            this.members.addAll(members);
        }
    }

    /**
     * Converts a given {@code Team} into this class for Jackson use.
     */
    public JsonAdaptedTeam(Team source) {
        assert source != null : "Source Team must not be null";
        name = source.getName();
        members.addAll(source.getPersonList().stream()
                .map(Person::getEmail)
                .collect(Collectors.toList()));
        assert !members.contains(null) : "Serialized team should not contain null member emails";
    }

    /**
     * Converts this Jackson-friendly adapted team object into the model's {@code Team} object.
     * Needs all persons to match email and link correct person object
     */
    public Team toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }

        if (name == "") {
            return Team.NONE;
        }

        if (!Team.isValidName(name)) {
            throw new IllegalValueException(Team.MESSAGE_CONSTRAINTS);
        }

        return new Team(name);
    }

    public List<Email> getMemberEmail() {

        assert !members.contains(null) : "Member email list contains nulls unexpectedly";
        return members;
    }

}
