package seedu.address.model.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.team.exceptions.DuplicateTeamException;
import seedu.address.model.team.exceptions.TeamNotFoundException;

public class UniqueTeamListTest {

    private final UniqueTeamList uniqueTeamList = new UniqueTeamList();

    @Test
    public void contains_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.contains(null));
    }

    @Test
    public void contains_teamNotInList_returnsFalse() {
        assertFalse(uniqueTeamList.contains(new Team("Team1")));
    }

    @Test
    public void contains_teamInList_returnsTrue() {
        Team team = new Team("Team1");
        uniqueTeamList.add(team);
        assertTrue(uniqueTeamList.contains(team));
    }

    @Test
    public void containsTeamWithName_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.containsTeamWithName(null));
    }

    @Test
    public void containsTeamWithName_teamNotInList_returnsFalse() {
        assertFalse(uniqueTeamList.containsTeamWithName("Team1"));
    }

    @Test
    public void containsTeamWithName_teamInList_returnsTrue() {
        Team team = new Team("Team1");
        uniqueTeamList.add(team);
        assertTrue(uniqueTeamList.containsTeamWithName("Team1"));
    }

    @Test
    public void getTeamByName_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.getTeamByName(null));
    }

    @Test
    public void getTeamByName_teamNotInList_returnsNull() {
        assertTrue(uniqueTeamList.getTeamByName("Team1") == null);
    }

    @Test
    public void getTeamByName_teamInList_returnsTeam() {
        Team team = new Team("Team1");
        uniqueTeamList.add(team);
        assertEquals(team, uniqueTeamList.getTeamByName("Team1"));
    }

    @Test
    public void add_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.add(null));
    }

    @Test
    public void add_duplicateTeam_throwsDuplicateTeamException() {
        Team team = new Team("Team1");
        uniqueTeamList.add(team);
        assertThrows(DuplicateTeamException.class, () -> uniqueTeamList.add(team));
    }

    @Test
    public void setTeam_nullTargetTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.setTeam(null, new Team("Team1")));
    }

    @Test
    public void setTeam_nullEditedTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.setTeam(new Team("Team1"), null));
    }

    @Test
    public void setTeam_targetTeamNotInList_throwsTeamNotFoundException() {
        Team team = new Team("Team1");
        assertThrows(TeamNotFoundException.class, () -> uniqueTeamList.setTeam(team, team));
    }

    @Test
    public void setTeam_editedTeamIsSameTeam_success() {
        Team team = new Team("Team1");
        uniqueTeamList.add(team);
        uniqueTeamList.setTeam(team, team);
        UniqueTeamList expectedUniqueTeamList = new UniqueTeamList();
        expectedUniqueTeamList.add(team);
        assertEquals(expectedUniqueTeamList, uniqueTeamList);
    }

    @Test
    public void setTeam_editedTeamHasDifferentName_success() {
        Team team = new Team("Team1");
        uniqueTeamList.add(team);
        Team editedTeam = new Team("Team2");
        uniqueTeamList.setTeam(team, editedTeam);
        UniqueTeamList expectedUniqueTeamList = new UniqueTeamList();
        expectedUniqueTeamList.add(editedTeam);
        assertEquals(expectedUniqueTeamList, uniqueTeamList);
    }

    @Test
    public void setTeam_editedTeamHasNonUniqueName_throwsDuplicateTeamException() {
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        uniqueTeamList.add(team1);
        uniqueTeamList.add(team2);
        assertThrows(DuplicateTeamException.class, () -> uniqueTeamList.setTeam(team1, team2));
    }

    @Test
    public void remove_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.remove(null));
    }

    @Test
    public void remove_teamDoesNotExist_throwsTeamNotFoundException() {
        Team team = new Team("Team1");
        assertThrows(TeamNotFoundException.class, () -> uniqueTeamList.remove(team));
    }

    @Test
    public void remove_existingTeam_removesTeam() {
        Team team = new Team("Team1");
        uniqueTeamList.add(team);
        uniqueTeamList.remove(team);
        UniqueTeamList expectedUniqueTeamList = new UniqueTeamList();
        assertEquals(expectedUniqueTeamList, uniqueTeamList);
    }

    @Test
    public void setTeams_nullUniqueTeamList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.setTeams((UniqueTeamList) null));
    }

    @Test
    public void setTeams_uniqueTeamList_replacesOwnListWithProvidedUniqueTeamList() {
        Team team = new Team("Team1");
        uniqueTeamList.add(team);
        UniqueTeamList expectedUniqueTeamList = new UniqueTeamList();
        Team team2 = new Team("Team2");
        expectedUniqueTeamList.add(team2);
        uniqueTeamList.setTeams(expectedUniqueTeamList);
        assertEquals(expectedUniqueTeamList, uniqueTeamList);
    }

    @Test
    public void setTeams_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.setTeams((List<Team>) null));
    }

    @Test
    public void setTeams_list_replacesOwnListWithProvidedList() {
        Team team = new Team("Team1");
        uniqueTeamList.add(team);
        List<Team> teamList = Collections.singletonList(team);
        uniqueTeamList.setTeams(teamList);
        UniqueTeamList expectedUniqueTeamList = new UniqueTeamList();
        expectedUniqueTeamList.add(team);
        assertEquals(expectedUniqueTeamList, uniqueTeamList);
    }

    @Test
    public void setTeams_listWithDuplicateTeams_throwsDuplicateTeamException() {
        Team team = new Team("Team1");
        List<Team> listWithDuplicateTeams = Arrays.asList(team, team);
        assertThrows(DuplicateTeamException.class, () -> uniqueTeamList.setTeams(listWithDuplicateTeams));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueTeamList.asUnmodifiableObservableList().remove(0));
    }
}
