package miw.fellowshipfungi.models.ask_models.recognitionmodels;

import java.util.Map;

public class MusshroomEntity {
    private final String id;
    private final String name;
    private final String img;
    private final String URLBase = "https://firebasestorage.googleapis.com/v0/b/fellowship-fungi.appspot.com/o/Setas%2F";

    public MusshroomEntity(Map<String, Object> dataMap) {
        this.id = (String) dataMap.get("idNode");
        this.name = (String) dataMap.get("textNode");
        this.img = (String) dataMap.get("img");
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getImgUrl() {
        return URLBase + this.img.replace(" ", "%20") + ".jpg?alt=media";
    }
}
