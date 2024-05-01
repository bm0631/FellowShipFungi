package miw.fellowshipfungi.models.ask;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class AnswerTestEntityTest {

    private Map<String, Object> mockDataMap;

    @Before
    public void setUp() {
        mockDataMap = new HashMap<>();
        mockDataMap.put("textAnswer", "Answer text");
        mockDataMap.put("isTrue", "true");
    }

    @Test
    public void testGetText() {
        AnswerTestEntity answerTestEntity = new AnswerTestEntity(mockDataMap);
        assertEquals("Answer text", answerTestEntity.getText());
    }

    @Test
    public void testIsTrue() {
        AnswerTestEntity answerTestEntity = new AnswerTestEntity(mockDataMap);
        assertEquals(true, answerTestEntity.isTrue());
    }
}
