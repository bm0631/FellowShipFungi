package miw.fellowshipfungi.models.profile;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ProfileDataTest {

    private ProfileData profileData;

    @Before
    public void setUp() {
        profileData = new ProfileData();
    }

    @Test
    public void testSetLengthCollection() {
        profileData.setLengthCollection(10);
        assertEquals(10, profileData.getLengthCollection());
    }

    @Test
    public void testSetStreak() {
        profileData.setStreak(5);
        assertEquals(5, profileData.getStreak());
    }

    @Test
    public void testSetBestResult() {
        profileData.setBestResult(0.75);
        assertEquals(0.75, profileData.getBestResult(), 0.001); // Use delta for double comparison
    }
}
