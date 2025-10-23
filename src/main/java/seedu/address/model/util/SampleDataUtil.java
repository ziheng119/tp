package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.team.Team;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();

        // Add teams first
        sampleAb.addTeam(new Team("F12-3"));
        sampleAb.addTeam(new Team("W08-1"));
        sampleAb.addTeam(new Team("T14-2"));

        // Get the actual team objects from the AddressBook
        Team teamF123 = sampleAb.getTeamByName("F12-3");
        Team teamW081 = sampleAb.getTeamByName("W08-1");
        Team teamT142 = sampleAb.getTeamByName("T14-2");

        // Create persons with the actual team objects from AddressBook
        Person[] samplePersons = {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Github("AlexYeoh"), teamF123),
            new Person(new Name("Bernice Yu"), new Phone("99272758"),
                    new Email("berniceyu@example.com"), new Github("BerniceYu"), teamF123),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Github("CharlotteO"), teamW081),
            new Person(new Name("David Li"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Github("DavidLi"), teamW081),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Github("IrfanIbrahim"), teamT142),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("royb@example.com"), new Github("RoyB"), Team.NONE)
        };

        // Add persons
        for (Person samplePerson : samplePersons) {
            sampleAb.addPerson(samplePerson);
        }

        // Add persons to their respective teams
        for (Person samplePerson : samplePersons) {
            if (!samplePerson.getTeam().equals(Team.NONE)) {
                Team personTeam = sampleAb.getTeamByName(samplePerson.getTeam().getName());
                if (personTeam != null) {
                    personTeam.addPerson(samplePerson);
                }
            }
        }
        return sampleAb;
    }
}
