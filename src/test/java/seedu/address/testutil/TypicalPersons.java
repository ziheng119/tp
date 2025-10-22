package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {
    public static final Team TEAM1 = new Team("F12-3");
    public static final Team TEAM2 = new Team("W08-1");
    public static final Team TEAM3 = new Team("T14-2");

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withGithub("alicepauline").withEmail("alice@example.com")
            .withPhone("94351253").withTeam(TEAM1).build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withGithub("bensonmeier").withEmail("johnd@example.com")
            .withPhone("98765432").withTeam(TEAM1).build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withGithub("carlkurz").withTeam(TEAM1).build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withGithub("danielmeier").withTeam(TEAM2).build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withGithub("ellemeyer").withTeam(TEAM2).build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withGithub("lydiakun").withTeam(TEAM2).build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withGithub("georgebest").withTeam(TEAM3).build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withGithub("hoonmeier").withTeam(TEAM1).build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withGithub("idamueller").withTeam(TEAM2).build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withGithub(VALID_GITHUB_AMY).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withGithub(VALID_GITHUB_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    static {
        TEAM1.setPersons(Arrays.asList(ALICE, BENSON, CARL));

        TEAM2.setPersons(Arrays.asList(DANIEL, ELLE, FIONA));

        TEAM3.setPersons(Arrays.asList(GEORGE));

    }

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Team team : getTypicalTeams()) {
            ab.addTeam(team);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Team> getTypicalTeams() {
        return new ArrayList<>(Arrays.asList(TEAM1, TEAM2, TEAM3));
    }
}
