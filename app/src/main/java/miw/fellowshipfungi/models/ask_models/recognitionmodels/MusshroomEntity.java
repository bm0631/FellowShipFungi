package miw.fellowshipfungi.models.ask_models.recognitionmodels;

import java.util.Map;

public class MusshroomEntity {
    private final String id;
    private final String textNode;
    private final String img;

    public MusshroomEntity(Map<String, Object> dataMap) {
        this.id = "";
        this.textNode = "";
        this.img = "";
    }

    public MusshroomEntity(String id, String textNode, String img) {
        this.id = "";
        this.textNode = "";
        this.img = "";
    }

    public String getTextNode() {
        return this.textNode;
    }
}
