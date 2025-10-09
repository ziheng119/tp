package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GithubTest {

    @Test
    public void constructor_validGithub_success() {
        Github github = new Github("valid-username");
        assertEquals("valid-username", github.value);
    }

    @Test
    public void constructor_invalidGithub_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Github(""));
        assertThrows(IllegalArgumentException.class, () -> new Github("-startshyphen"));
        assertThrows(IllegalArgumentException.class, () -> new Github("endshyphen-"));
        assertThrows(IllegalArgumentException.class, () -> new Github("double--hyphen"));
        assertThrows(IllegalArgumentException.class, () -> new Github(
            "toolongusername1234567890123456789012345678901234567890"
        ));
    }

    @Test
    public void isValidGithub() {
        // valid usernames
        assertTrue(Github.isValidGithub("octocat"));
        assertTrue(Github.isValidGithub("octo-cat"));
        assertTrue(Github.isValidGithub("OCTOcat123"));
        assertTrue(Github.isValidGithub("a")); // minimum length
        assertTrue(Github.isValidGithub("a123456789012345678901234567890123456789")); // 39 chars

        // invalid usernames
        assertTrue(!Github.isValidGithub(""));
        assertTrue(!Github.isValidGithub("-octocat"));
        assertTrue(!Github.isValidGithub("octocat-"));
        assertTrue(!Github.isValidGithub("octo--cat"));
        assertTrue(!Github.isValidGithub("octo_cat")); // underscore not allowed
        assertTrue(!Github.isValidGithub("toolongusername1234567890123456789012345678901234567890")); // >39 chars
    }

    @Test
    public void equals_and_hashCode() {
        Github github1 = new Github("octocat");
        Github github2 = new Github("octocat");
        Github github3 = new Github("octo-cat");

        assertEquals(github1, github2);
        assertTrue(github1.hashCode() == github2.hashCode());
        assertTrue(!github1.equals(github3));
    }

    @Test
    public void toString_returnsValue() {
        Github github = new Github("octocat");
        assertEquals("octocat", github.toString());
    }
}
