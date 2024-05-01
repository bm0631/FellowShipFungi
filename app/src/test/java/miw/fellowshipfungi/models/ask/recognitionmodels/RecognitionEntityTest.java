package miw.fellowshipfungi.models.ask.recognitionmodels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecognitionEntityTest {

    private static final String ASK_TEXT = "What is this mushroom?";
    private static final String MUSHROOM_ID = "Species_001";
    private static final String MUSHROOM_NAME = "Amanita muscaria";
    private static final String MUSHROOM_IMG = "amanita_muscaria";

    private RecognitionEntity recognitionEntity;

    @Before
    public void setUp() {
        recognitionEntity = new RecognitionEntity();
    }

    @Test
    public void testAddAnswer() {
        Map<String, Object> answerData = new HashMap<>();
        answerData.put("idNode", "Answer_001");
        answerData.put("nextNodes", "Specie_001");
        answerData.put("textNode", "It's a mushroom.");

        recognitionEntity.addAnswer(answerData);

        List<AnswerEntity> answerEntities = recognitionEntity.getAnswerEntities();
        assertNotNull(answerEntities);
        assertEquals(1, answerEntities.size());
        assertEquals("It's a mushroom.", answerEntities.get(0).getText());
    }

    @Test
    public void testSetAskEntity() {
        Map<String, Object> askData = new HashMap<>();
        askData.put("id", "Ask_001");
        askData.put("textNode", ASK_TEXT);

        recognitionEntity.setAskEntity(askData);

        assertEquals(ASK_TEXT, recognitionEntity.getAskText());
    }

    @Test
    public void testSetMusshroomEntity() {
        Map<String, Object> mushroomData = new HashMap<>();
        mushroomData.put("idNode", MUSHROOM_ID);
        mushroomData.put("textNode", MUSHROOM_NAME);
        mushroomData.put("img", MUSHROOM_IMG);

        String expectImgURL = "https://firebasestorage.googleapis.com/v0/b/fellowship-fungi.appspot.com/o/Setas%2F" + MUSHROOM_IMG.replace(" ", "%20") + ".jpg?alt=media";
        recognitionEntity.setMusshroomEntity(mushroomData);

        assertEquals(MUSHROOM_NAME, recognitionEntity.getMusshroomName());
        assertEquals(expectImgURL, recognitionEntity.getMusshroomImgUrl());
    }
}
