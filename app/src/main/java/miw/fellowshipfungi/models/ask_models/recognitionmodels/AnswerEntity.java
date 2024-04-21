package miw.fellowshipfungi.models.ask_models.recognitionmodels;

import java.util.Map;

public class AnswerEntity {
    private String id;
    private String nextNode;
    private String text;

    public AnswerEntity(Map<String, Object> dataMap) {
        this.id = (String) dataMap.get("idNode");
        this.nextNode = (String) dataMap.get("nextNodes");
        this.text = (String) dataMap.get("textNode");
    }


    public String getNextNode() {
        return this.nextNode;
    }

    public String getText() {
        return this.text;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AnswerEntity{" +
                "id='" + id + '\'' +
                ", nextNode='" + nextNode + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
