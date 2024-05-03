package miw.fellowshipfungi.models.ask.recognitionmodels;

import java.util.Map;

public class AnswerEntity {

    private String nextNode;
    private String text;

    public AnswerEntity(Map<String, Object> dataMap) {
        this.nextNode = (String) dataMap.get("nextNodes");
        this.text = (String) dataMap.get("textNode");
    }


    public String getNextNode() {
        return this.nextNode;
    }

    public String getText() {
        return this.text;
    }




}
