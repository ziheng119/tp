package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_TEAM = "Teams list contains duplicate team(s).";
    public static final String MISSING_PERSON_MESSAGE_FORMAT = "Member %s not found in address book!";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedTeam> teams = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(
            @JsonProperty("persons") List<JsonAdaptedPerson> persons,
            @JsonProperty("teams") List<JsonAdaptedTeam> teams) {
        if (persons != null) {
            this.persons.addAll(persons);
        }
        if (teams != null) {
            this.teams.addAll(teams);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));

        teams.addAll(source.getTeamList().stream()
                .map(JsonAdaptedTeam::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        Map<String, Team> teamMap = new HashMap<>();
        Map<String, List<Email>> teamMemberMap = new HashMap<>();

        for (JsonAdaptedTeam jsonAdaptedTeam : teams) {
            Team team = jsonAdaptedTeam.toModelType();
            if (addressBook.hasTeam(team)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TEAM);
            }
            teamMap.put(team.getName(), team);
            teamMemberMap.put(team.getName(), jsonAdaptedTeam.getMemberEmail());
            addressBook.addTeam(team);
        }
        teamMap.put("", Team.NONE);

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType(teamMap);
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

        for (String teamName : teamMemberMap.keySet()) {
            Team team = teamMap.get(teamName);
            List<Email> emailList = teamMemberMap.get(teamName);
            List<Person> teamPersonList = getPersonList(emailList, addressBook.getPersonList());
            team.setPersons(teamPersonList);
        }

        return addressBook;
    }

    private List<Person> getPersonList(List<Email> emailList, ObservableList<Person> allPersons)
            throws IllegalValueException {
        List<Person> memberList = new ArrayList<>();
        for (Email email : emailList) {
            Person found = allPersons.stream()
                    .filter(person -> person.getEmail().equals(email))
                    .findFirst()
                    .orElse(null);
            if (found == null) {
                throw new IllegalValueException(String.format(MISSING_PERSON_MESSAGE_FORMAT, email));
            }
            memberList.add(found);
        }

        return memberList;
    }
}
