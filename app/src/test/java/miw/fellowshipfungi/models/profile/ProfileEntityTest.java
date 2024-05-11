package miw.fellowshipfungi.models.profile;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ProfileEntityTest {

    private ProfileEntity profileEntity;

    @Before
    public void setUp() {
        profileEntity = new ProfileEntity();
    }

    @Test
    public void testSetLengthCollection() {
        profileEntity.setLengthCollection(10);
        assertEquals(10, profileEntity.getLengthCollection());
    }

    @Test
    public void testSetStreak() {
        profileEntity.setStreak(5);
        assertEquals(5, profileEntity.getStreak());
    }

    @Test
    public void testSetBestResult() {
        profileEntity.setBestResult(0.75);
        assertEquals(0.75, profileEntity.getBestResult(), 0.001); // Use delta for double comparison
    }

    @Test
    public void testSetUsername() {
        profileEntity.setUsername("Prueba");
        assertEquals("Prueba", profileEntity.getUsername());
    }
}
