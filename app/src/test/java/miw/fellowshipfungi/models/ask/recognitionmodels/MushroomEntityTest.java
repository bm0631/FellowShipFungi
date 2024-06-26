package miw.fellowshipfungi.models.ask.recognitionmodels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

public class MushroomEntityTest {

    private static final String ID = "mushroomId";
    private static final String NAME = "mushroomName";
    private static final String IMG = "mushroomImg";

    @Mock
    private Map<String, Object> mockDataMap;


    @Test
    public void testConstructor() {

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("idNode", ID);
        dataMap.put("textNode", NAME);
        dataMap.put("img", IMG);
        String expectImgURL = "https://firebasestorage.googleapis.com/v0/b/fellowship-fungi.appspot.com/o/Setas%2FmushroomImg.jpg?alt=media";
        MushroomEntity mushroomEntity = new MushroomEntity(dataMap);

        assertNotNull(mushroomEntity);
        assertEquals(ID, mushroomEntity.getId());
        assertEquals(NAME, mushroomEntity.getName());
        assertEquals(expectImgURL, mushroomEntity.getImgUrl());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullDataMap() {
        new MushroomEntity(null); // Expecting NullPointerException
    }

    @Test(expected = ClassCastException.class)
    public void testConstructorWithInvalidDataMap() {

        Map<String, Object> invalidDataMap = new HashMap<>();
        invalidDataMap.put("idNode", ID);
        invalidDataMap.put("textNode", 1234); // Invalid data type for textNode
        invalidDataMap.put("img", IMG);

        new MushroomEntity(invalidDataMap); // Expecting ClassCastException
    }
}
