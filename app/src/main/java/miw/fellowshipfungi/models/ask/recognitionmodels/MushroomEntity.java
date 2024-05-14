package miw.fellowshipfungi.models.ask.recognitionmodels;

import java.util.Map;

import miw.fellowshipfungi.models.ImgStorageEntity;

public class MushroomEntity extends ImgStorageEntity {
    private static final String folder = "Setas%2F";
    private final String id;
    private final String name;


    public MushroomEntity(Map<String, Object> dataMap) {
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


    @Override
    public String getFolder() {
        return folder;
    }
}
