package miw.fellowshipfungi.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.util.Map;

public class EnconterEntityTest {

    @Test
    public void testGetSetDate() {
        String specieId = "Species_001";
        String date = "27-04-2024";
        EnconterEntity enconterEntity = new EnconterEntity(specieId);

        enconterEntity.setDate(date);
        assertEquals(date, enconterEntity.getDate());
    }

    @Test
    public void testGetSetLocation() {
        String specieId = "Species_002";
        String location = "Forest";
        EnconterEntity enconterEntity = new EnconterEntity(specieId);

        enconterEntity.setLocation(location);
        assertEquals(location, enconterEntity.getLocation());
    }

    @Test
    public void testGetSetWeather() {
        String specieId = "Species_003";
        String weather = "Sunny";
        EnconterEntity enconterEntity = new EnconterEntity(specieId);

        enconterEntity.setWeather(weather);
        assertEquals(weather, enconterEntity.getWeather());
    }

    @Test
    public void testGetSetNameImg() {
        String specieId = "Species_004";
        String nameImg = "Fungi.jpg";
        EnconterEntity enconterEntity = new EnconterEntity(specieId);

        enconterEntity.setImg(nameImg);
        assertEquals(nameImg, enconterEntity.getImg());
    }


    @Test
    public void testGetMap() {
        String specieId = "Species_001";
        String date = "27-04-2024";
        String location = "Forest";
        String weather = "Sunny";
        String nameImg = "fungi.jpg";

        EnconterEntity enconterEntity = new EnconterEntity(specieId);
        enconterEntity.setDate(date);
        enconterEntity.setLocation(location);
        enconterEntity.setWeather(weather);
        enconterEntity.setImg(nameImg);

        Map<String, Object> map = enconterEntity.getMap();

        assertNotNull(map);
        assertEquals(specieId, map.get("speciedId"));
        assertEquals(date, map.get("date"));
        assertEquals(location, map.get("location"));
        assertEquals(weather, map.get("weather"));
        assertEquals(nameImg, map.get("nameImg"));
    }


}
