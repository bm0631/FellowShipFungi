package miw.fellowshipfungi.models.ask;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestEntityTest {

    private Map<String, Object> mockDataMap;

    @Before
    public void setUp() {
        mockDataMap = new HashMap<>();
        mockDataMap.put("id", "1");
        mockDataMap.put("text", "Question text");
        mockDataMap.put("img", "image_path");
        List<Map<String, Object>> answersList = new ArrayList<>();
        Map<String, Object> answer1 = new HashMap<>();
        answer1.put("textAnswer", "Answer 1");
        answer1.put("isTrue", "true");
        answersList.add(answer1);
        Map<String, Object> answer2 = new HashMap<>();
        answer2.put("textAnswer", "Answer 2");
        answer2.put("isTrue", "false");
        answersList.add(answer2);
        mockDataMap.put("Answers", answersList);
    }

    @Test
    public void testGetTextQuestion() {
        TestEntity testEntity = new TestEntity(mockDataMap);
        assertEquals("Question text", testEntity.getTextQuestion());
    }

    @Test
    public void testGetTextAnswer() {
        TestEntity testEntity = new TestEntity(mockDataMap);
        assertEquals("Answer 1", testEntity.getTextAnwser(0));
        assertEquals("Answer 2", testEntity.getTextAnwser(1));
    }

    @Test
    public void testGetIsTrueAnswer() {
        TestEntity testEntity = new TestEntity(mockDataMap);
        assertEquals(true, testEntity.getIsTrueAnwser(0));
        assertEquals(false, testEntity.getIsTrueAnwser(1));
    }

    @Test
    public void testGetImgUrl() {
        TestEntity testEntity = new TestEntity(mockDataMap);
        assertEquals("https://firebasestorage.googleapis.com/v0/b/fellowship-fungi.appspot.com/o/Questions%2Fimage_path.jpg?alt=media", testEntity.getImgUrl());
    }
}
