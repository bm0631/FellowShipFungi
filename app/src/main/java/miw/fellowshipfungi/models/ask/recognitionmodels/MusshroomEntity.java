package miw.fellowshipfungi.models.ask.recognitionmodels;

import java.util.Map;

import miw.fellowshipfungi.models.ImgStorageEntity;

public class MusshroomEntity extends ImgStorageEntity {
    private final String id;
    private final String name;
    private final String img;
    private static final String folder = "Setas%2F";

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

    private String getImg() {
        return this.img.replace(" ", "%20");
    }

    @Override
    public String getFolderAndImg() {
        return folder+this.getImg();
    }
}
