package miw.fellowshipfungi.models.ask_models.recognitionmodels;

import java.util.Map;

public class AnswerEntity {
    private String id;
    private String nextNode;
    private String text;

    public AnswerEntity(Map<String, Object> dataMap) {
        this.id = (String) dataMap.get("idNode");
        this.nextNode = (String) dataMap.get("nextNode");
        this.text = (String) dataMap.get("textNode");
    }

    public String getNextNode() {
        return nextNode;
    }

    public NodeTypes typeNextNode() {
        return this.nextNode.contentEquals("Ask_") ? NodeTypes.Ask : NodeTypes.Specie;
    }

    public String getText() {
        return text;
    }
}
