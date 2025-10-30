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
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();

        TeamParsing teamParsing = parseTeams(addressBook);
        addPersons(addressBook, teamParsing.teamMap);
        populateTeamsFromMembers(addressBook, teamParsing.teamMap, teamParsing.teamMemberMap);

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

    /**
     * Parses teams from JSON into the address book, returning maps used for wiring memberships later.
     */
    private TeamParsing parseTeams(AddressBook addressBook) throws IllegalValueException {
        Map<String, Team> teamMap = new HashMap<>();
        Map<String, List<Email>> teamMemberMap = new HashMap<>();

        for (JsonAdaptedTeam jsonAdaptedTeam : teams) {
            Team team = jsonAdaptedTeam.toModelType();
            throwIfDuplicateTeam(addressBook, team);
            addressBook.addTeam(team);

            teamMemberMap.put(team.getName(), jsonAdaptedTeam.getMemberEmail());
            teamMap.put(team.getName(), team);
        }
        teamMap.put("", Team.NONE);

        return new TeamParsing(teamMap, teamMemberMap);
    }


    /**
     * Adds persons to the address book after converting them from their JSON form.
     */
    private void addPersons(AddressBook addressBook, Map<String, Team> teamMap) throws IllegalValueException {
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType(teamMap);
            throwIfDuplicatePerson(addressBook, person);
            addressBook.addPerson(person);
        }
    }
    /**
     * Wires team memberships using the previously captured email lists per team.
     */
    private void populateTeamsFromMembers(AddressBook addressBook, Map<String, Team> teamMap,
            Map<String, List<Email>> teamMemberMap) throws IllegalValueException {
        for (Map.Entry<String, List<Email>> entry : teamMemberMap.entrySet()) {
            Team team = teamMap.get(entry.getKey());
            List<Email> emailList = entry.getValue();
            List<Person> teamPersonList = resolvePersonsByEmail(emailList, addressBook.getPersonList());
            team.setPersons(teamPersonList);
        }
    }

    /** Resolves a list of persons by their emails; fails if any email is missing. */
    private static List<Person> resolvePersonsByEmail(List<Email> emailList, ObservableList<Person> allPersons)
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

    /** Throws if the given team already exists in the address book. */
    private static void throwIfDuplicateTeam(AddressBook addressBook, Team team) throws IllegalValueException {
        if (addressBook.hasTeam(team)) {
            throw new IllegalValueException(MESSAGE_DUPLICATE_TEAM);
        }
    }

    /** Throws if the given person already exists in the address book (identity-based). */
    private static void throwIfDuplicatePerson(AddressBook addressBook, Person person) throws IllegalValueException {
        if (addressBook.hasPerson(person)) {
            throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
        }
    }

    /** Simple container for team parsing artifacts. */
    private static class TeamParsing {
        private final Map<String, Team> teamMap;
        private final Map<String, List<Email>> teamMemberMap;

        private TeamParsing(Map<String, Team> teamMap, Map<String, List<Email>> teamMemberMap) {
            this.teamMap = teamMap;
            this.teamMemberMap = teamMemberMap;
        }
    }
}
