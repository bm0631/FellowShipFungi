package miw.fellowshipfungi.models.ask_models;

import java.util.Map;

public class AnswerTestEntity {
    private String text;
    private boolean istrue;

    public AnswerTestEntity(Map<String, Object> dataMap){
        this.text=(String)dataMap.get("textAnswer");
        this.istrue=Boolean.getBoolean((String)dataMap.get("istrue"));
    }


    @Override
    public String toString() {
        return "AnswerTestEntity{" +
                "text='" + text + '\'' +
                ", istrue=" + istrue +
                '}';
    }
}
