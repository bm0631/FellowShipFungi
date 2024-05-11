package miw.fellowshipfungi.models;

import java.util.Map;

public class CuriosityEntity extends ImgStorageEntity {
    private static final String folder = "Curiosities%2F";
    private String text;

    public CuriosityEntity(Map<String, Object> dataMap) {
        super();
        this.text = (String) dataMap.get("text");
        this.img = (String) dataMap.get("img");
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String getFolder() {
        return this.folder;
    }


}
