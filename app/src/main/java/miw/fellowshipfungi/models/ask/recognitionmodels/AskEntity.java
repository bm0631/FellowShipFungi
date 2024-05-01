package miw.fellowshipfungi.models.ask.recognitionmodels;

import java.util.Map;

public class AskEntity {
    private String id;


    private String text;

    public AskEntity(Map<String, Object> dataMap) {
        this.id = (String) dataMap.get("id");
        this.text = (String) dataMap.get("textNode");
    }

    public String getText() {
        return text;
    }

}
