package miw.fellowshipfungi.models;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;


import static org.junit.Assert.assertNotNull;

import org.mockito.Mock;


public class CuriosityEntityTest {

    private static final String ID = "CuriosityId";
    private static final String TEXT = "Curioso";
    private static final String IMG = "CuriosityIMG";

    @Mock
    private Map<String, Object> mockDataMap;


    @Test
    public void testConstructor() {

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", ID);
        dataMap.put("text", TEXT);
        dataMap.put("img", IMG);
        String expectImgURL = "https://firebasestorage.googleapis.com/v0/b/fellowship-fungi.appspot.com/o/Curiosities%2FCuriosityIMG.jpg?alt=media";
        CuriosityEntity curiosityEntity = new CuriosityEntity(dataMap);

        assertNotNull(curiosityEntity);
        assertEquals(TEXT, curiosityEntity.getText());
        assertEquals(expectImgURL, curiosityEntity.getImgUrl());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullDataMap() {
        new CuriosityEntity(null); // Expecting NullPointerException
    }

    @Test(expected = ClassCastException.class)
    public void testConstructorWithInvalidDataMap() {

        Map<String, Object> invalidDataMap = new HashMap<>();
        invalidDataMap.put("id", ID);
        invalidDataMap.put("text", 1234); // Invalid data type for textNode
        invalidDataMap.put("img", IMG);

        new CuriosityEntity(invalidDataMap); // Expecting ClassCastException
    }
}
