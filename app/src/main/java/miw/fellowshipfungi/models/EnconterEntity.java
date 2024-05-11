package miw.fellowshipfungi.models;

import java.util.HashMap;
import java.util.Map;

public class EnconterEntity extends ImgStorageEntity {
    private static final String folder = "EncontersImg%2F";
    private String specieId;
    private String date;
    private String location;
    private String weather;

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



    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("speciedId", this.getSpecieId());
        map.put("date", this.getDate());
        map.put("location", this.getLocation());
        map.put("weather", this.getWeather());
        map.put("nameImg", this.getImg());

        return map;
    }


    @Override
    public String getFolder() {
        return this.folder ;
    }


    protected boolean hasImg() {
        return this.getImg() != null;
    }
}
