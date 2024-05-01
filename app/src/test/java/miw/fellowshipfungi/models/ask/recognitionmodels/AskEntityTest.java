package miw.fellowshipfungi.models.ask.recognitionmodels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

public class AskEntityTest {

    private static final String ID = "askId";
    private static final String TEXT = "askText";

    @Mock
    private Map<String, Object> mockDataMap;


    @Test
    public void testConstructor() {

        mockDataMap = new HashMap<>();
        mockDataMap.put("id", ID);
        mockDataMap.put("textNode", TEXT);


        AskEntity askEntity = new AskEntity(mockDataMap);


        assertNotNull(askEntity);
        assertEquals(TEXT, askEntity.getText());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullDataMap() {
        new AskEntity(null); // Expecting NullPointerException
    }

    @Test(expected = ClassCastException.class)
    public void testConstructorWithInvalidDataMap() {
        // Mock invalid data map
        mockDataMap = new HashMap<>();
        mockDataMap.put("id", ID);
        mockDataMap.put("textNode", 1234);

        new AskEntity(mockDataMap);
    }
}
