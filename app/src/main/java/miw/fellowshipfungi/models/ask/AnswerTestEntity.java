package miw.fellowshipfungi.models.ask;

import java.util.Map;

public class AnswerTestEntity {
    private String text;
    private boolean isTrue;

    public AnswerTestEntity(Map<String, Object> dataMap) {
        this.text = (String) dataMap.get("textAnswer");
        this.isTrue = Boolean.parseBoolean((String) dataMap.get("isTrue"));
    }


    public String getText() {
        return text;
    }

    public boolean isTrue() {
        return isTrue;
    }
}
