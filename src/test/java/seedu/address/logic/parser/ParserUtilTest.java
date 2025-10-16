package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class ParserUtilTest {
    private static final String WHITESPACE = " \t\r\n";

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_GITHUB = "-rachel";
    private static final String INVALID_EMAIL = "example.com";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_GITHUB = "rachelwalker";
    private static final String VALID_EMAIL = "rachel@example.com";

    private static final String INVALID_TEAM_NAME = "Team@123";
    private static final String VALID_TEAM_NAME = "Team 123";
    private static final String TEAM_NAME_WHITESPACE = WHITESPACE + VALID_TEAM_NAME + WHITESPACE;

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseGithub_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGithub((String) null));
    }

    @Test
    public void parseGithub_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGithub(INVALID_GITHUB));
    }

    @Test
    public void parseGithub_validValueWithoutWhitespace_returnsGithub() throws Exception {
        Github expectedGithub = new Github(VALID_GITHUB);
        assertEquals(expectedGithub, ParserUtil.parseGithub(VALID_GITHUB));
    }

    @Test
    public void parseGithub_validValueWithWhitespace_returnsTrimmedGithub() throws Exception {
        String githubWithWhitespace = WHITESPACE + VALID_GITHUB + WHITESPACE;
        Github expectedGithub = new Github(VALID_GITHUB);
        assertEquals(expectedGithub, ParserUtil.parseGithub(githubWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTeamName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTeamName(null));
    }

    @Test
    public void parseTeamName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTeamName(INVALID_TEAM_NAME));
        assertThrows(ParseException.class, () -> ParserUtil.parseTeamName("")); // blank
        assertThrows(ParseException.class, () -> ParserUtil.parseTeamName("   ")); // only spaces
    }

    @Test
    public void parseTeamName_validValueWithoutWhitespace_returnsTrimmedName() throws Exception {
        assertEquals(VALID_TEAM_NAME, ParserUtil.parseTeamName(VALID_TEAM_NAME));
    }

    @Test
    public void parseTeamName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        assertEquals(VALID_TEAM_NAME, ParserUtil.parseTeamName(TEAM_NAME_WHITESPACE));
    }
}
