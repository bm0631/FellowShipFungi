package miw.fellowshipfungi.models;

import java.util.Map;

public class CuriosityEntity {

    private final String URLBase = "https://firebasestorage.googleapis.com/v0/b/fellowship-fungi.appspot.com/o/Curiosities%2F";
    private String text;
    private String img;

    public CuriosityEntity(Map<String, Object> dataMap) {
        this.text = (String) dataMap.get("text");
        this.img = (String) dataMap.get("img");
    }

    public String getText() {
        return this.text;
    }

    public String getImgUrl() {
        return URLBase + this.img.replace(" ", "%20") + ".jpg?alt=media";
    }



}
