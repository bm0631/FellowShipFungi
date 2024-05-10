package miw.fellowshipfungi.models;

import java.util.HashMap;
import java.util.Map;

public class EnconterEntity {
    private String specieId;
    private String date;
    private String location;
    private String weather;

    private String nameImg;

    public EnconterEntity(String specieId) {
        this.specieId = specieId;
    }

    public String getSpecieId() {
        return specieId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getNameImg() {
        return nameImg;
    }

    public void setNameImg(String nameImg) {
        this.nameImg = nameImg;
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("speciedId", this.getSpecieId());
        map.put("date", this.getDate());
        map.put("location", this.getLocation());
        map.put("weather", this.getWeather());
        map.put("nameImg", this.getNameImg());

        return map;
    }


}
