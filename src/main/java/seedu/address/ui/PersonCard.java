package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As
     * a consequence, UI elements' variable names cannot be set to such keywords or an exception
     * will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on
     *      AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane team;
    @FXML
    private Label phone;
    @FXML
    private ImageView githubIcon1;
    @FXML
    private ImageView githubIcon2;
    @FXML
    private Hyperlink github;
    @FXML
    private Label email;
    @FXML
    private HBox teamGithubBox;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        if (person.getTeam() != Team.NONE) {
            String teamName = person.getTeamName();
            Hyperlink teamLink = new Hyperlink(teamName);
            teamLink.setOnAction(event -> openTeamUrl(teamName));
            teamGithubBox.getChildren().add(teamLink);
            try {
                Image icon = new Image(getClass().getResourceAsStream("/images/github_icon.png"));
                if (githubIcon1 != null) {
                    githubIcon1.setImage(icon);
                    githubIcon1.setFitWidth(16);
                    githubIcon1.setFitHeight(16);
                    githubIcon1.setPreserveRatio(true);
                }
            } catch (Exception e) {
                // ignore missing image in tests; icon is decorative
            }
            teamGithubBox.setVisible(true);
            teamGithubBox.setManaged(true);
        } else {
            teamGithubBox.setVisible(false);
            teamGithubBox.setManaged(false);
        }

        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        github.setText(person.getGithub().value);
        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/github_icon.png"));
            if (githubIcon2 != null) {
                githubIcon2.setImage(icon);
                githubIcon2.setFitWidth(16);
                githubIcon2.setFitHeight(16);
                githubIcon2.setPreserveRatio(true);
            }
        } catch (Exception e) {
            // ignore missing image in tests; icon is decorative
        }
        github.setOnAction(event -> {
            try {
                String url = "https://github.com/" + person.getGithub().value;
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Build the external URL for a team.
     */
    private String buildTeamUrl(String teamName) {
        final String baseUrl = "https://github.com/AY2526S1-CS2103T-";
        String encoded = URLEncoder.encode(teamName, StandardCharsets.UTF_8);
        return baseUrl + encoded;
    }

    private void openTeamUrl(String teamName) {
        String url = buildTeamUrl(teamName);
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                Runtime.getRuntime().exec(new String[] {"cmd", "/c", "start", "\"\"", url});
            }
        } catch (IOException | URISyntaxException e) {
            System.err.println("Failed to open team URL: " + e.getMessage());
        }
    }
}
