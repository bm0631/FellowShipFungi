package miw.fellowshipfungi.models.profile;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import miw.fellowshipfungi.models.ask.recognitionmodels.RecognitionEntity;

public class EnconterCollectionEntityTest {

    private final String enconterID = "123456";
    private final String speciedId = "789";
    private final String date = "2024-04-27";
    private final String location = "Forest";
    private final String weather = "Sunny";
    private final String mushroomId = "456";
    private final String mushroomName = "Agaricus bisporus";
    private final String imgUrl = "Agaricus bisporus";
    private EncounterCollectionEntity enconterCollectionEntity;

    @Before
    public void setUp() {

        Map<String, Object> dataMapEnconter = new HashMap<>();
        dataMapEnconter.put("speciedId", speciedId);
        dataMapEnconter.put("date", date);
        dataMapEnconter.put("location", location);
        dataMapEnconter.put("weather", weather);


        Map<String, Object> dataMapMushroom = new HashMap<>();
        dataMapMushroom.put("idNode", mushroomId);
        dataMapMushroom.put("textNode", mushroomName);
        dataMapMushroom.put("img", imgUrl);


        RecognitionEntity recognitionEntity = new RecognitionEntity();
        recognitionEntity.setMushroomEntity(dataMapMushroom);


        enconterCollectionEntity = new EncounterCollectionEntity(enconterID, dataMapEnconter, recognitionEntity);
    }

    @Test
    public void testGetEnconterID() {
        assertEquals(enconterID, enconterCollectionEntity.getEncounterID());
    }

    @Test
    public void testGetSpeciedId() {
        assertEquals(speciedId, enconterCollectionEntity.getSpecieId());
    }

    @Test
    public void testGetDate() {
        assertEquals(date, enconterCollectionEntity.getDate());
    }

    @Test
    public void testGetLocation() {
        assertEquals(location, enconterCollectionEntity.getLocation());
    }

    @Test
    public void testGetWeather() {
        assertEquals(weather, enconterCollectionEntity.getWeather());
    }

    @Test
    public void testGetNameMussroom() {
        assertEquals(mushroomName, enconterCollectionEntity.getNameMussroom());
    }

    @Test
    public void testGetImgUrl() {
        assertEquals("https://firebasestorage.googleapis.com/v0/b/fellowship-fungi.appspot.com/o/Setas%2FAgaricus%20bisporus.jpg?alt=media", enconterCollectionEntity.getImgUrl());
    }
}
