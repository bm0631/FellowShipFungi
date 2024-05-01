package miw.fellowshipfungi.models.ask.recognitionmodels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;


public class AnswerEntityTest {
    private static final String ID = "answerId";
    private static final String NEXT_NODE = "nextNodeId";
    private static final String TEXT = "answerText";

    @Mock
    private Map<String, Object> mockDataMap;


    @Test
    public void testConstructor() {

        mockDataMap = new HashMap<>();
        mockDataMap.put("idNode", ID);
        mockDataMap.put("nextNodes", NEXT_NODE);
        mockDataMap.put("textNode", TEXT);


        AnswerEntity answerEntity = new AnswerEntity(mockDataMap);


        assertNotNull(answerEntity);
        assertEquals(NEXT_NODE, answerEntity.getNextNode());
        assertEquals(TEXT, answerEntity.getText());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullDataMap() {
        new AnswerEntity(null); // Expecting NullPointerException
    }

    @Test(expected = ClassCastException.class)
    public void testConstructorWithInvalidDataMap() {

        mockDataMap = new HashMap<>();
        mockDataMap.put("idNode", ID);
        mockDataMap.put("nextNodes", NEXT_NODE);
        mockDataMap.put("textNode", 1234); // Invalid data type for textNode

        new AnswerEntity(mockDataMap); // Expecting ClassCastException
    }
}

