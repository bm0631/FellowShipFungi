package miw.fellowshipfungi.models.ask_models;

import java.util.Map;

public class AnswerTestEntity {
    private String text;
    private boolean isTrue;

    public AnswerTestEntity(Map<String, Object> dataMap) {
        this.text = (String) dataMap.get("textAnswer");
        this.isTrue = Boolean.parseBoolean((String) dataMap.get("isTrue"));
    }


    @Override
    public String toString() {
        return "AnswerTestEntity{" +
                "text='" + text + '\'' +
                ", istrue=" + isTrue +
                '}';
    }

    public String getText() {
        return text;
    }

    public boolean isTrue() {
        return isTrue;
    }
}
