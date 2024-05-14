package miw.fellowshipfungi.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.util.Map;

public class EncounterEntityTest {

    @Test
    public void testGetSetDate() {
        String specieId = "Species_001";
        String date = "27-04-2024";
        EncounterEntity encounterEntity = new EncounterEntity(specieId);

        encounterEntity.setDate(date);
        assertEquals(date, encounterEntity.getDate());
    }

    @Test
    public void testGetSetLocation() {
        String specieId = "Species_002";
        String location = "Forest";
        EncounterEntity encounterEntity = new EncounterEntity(specieId);

        encounterEntity.setLocation(location);
        assertEquals(location, encounterEntity.getLocation());
    }

    @Test
    public void testGetSetWeather() {
        String specieId = "Species_003";
        String weather = "Sunny";
        EncounterEntity encounterEntity = new EncounterEntity(specieId);

        encounterEntity.setWeather(weather);
        assertEquals(weather, encounterEntity.getWeather());
    }

    @Test
    public void testGetSetNameImg() {
        String specieId = "Species_004";
        String nameImg = "Fungi.jpg";
        EncounterEntity encounterEntity = new EncounterEntity(specieId);

        encounterEntity.setImg(nameImg);
        assertEquals(nameImg, encounterEntity.getImg());
    }


    @Test
    public void testGetMap() {
        String specieId = "Species_001";
        String date = "27-04-2024";
        String location = "Forest";
        String weather = "Sunny";
        String nameImg = "fungi.jpg";

        EncounterEntity encounterEntity = new EncounterEntity(specieId);
        encounterEntity.setDate(date);
        encounterEntity.setLocation(location);
        encounterEntity.setWeather(weather);
        encounterEntity.setImg(nameImg);

        Map<String, Object> map = encounterEntity.getMap();

        assertNotNull(map);
        assertEquals(specieId, map.get("speciedId"));
        assertEquals(date, map.get("date"));
        assertEquals(location, map.get("location"));
        assertEquals(weather, map.get("weather"));
        assertEquals(nameImg, map.get("nameImg"));
    }


}
