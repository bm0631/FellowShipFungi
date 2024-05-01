package miw.fellowshipfungi.models.ask;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TestEntity {
    private final String URLBase = "https://firebasestorage.googleapis.com/v0/b/fellowship-fungi.appspot.com/o/Questions%2F";
    private String id;
    private String question;
    private String img;
    private List<AnswerTestEntity> answers;

    public TestEntity(Map<String, Object> dataMap) {

        this.id = (String) dataMap.get("id");
        this.question = (String) dataMap.get("text");
        this.img = (String) dataMap.get("img");
        this.answers = new ArrayList<AnswerTestEntity>();
        List<Map<String, Object>> answersMapList = (List<Map<String, Object>>) dataMap.get("Answers");
        for (Map<String, Object> answerMap : answersMapList) {
            AnswerTestEntity answer = new AnswerTestEntity(answerMap);
            this.answers.add(answer);
        }

    }

    public String getTextQuestion() {
        return this.question;
    }

    public String getTextAnwser(int index) {
        return answers.get(index).getText();
    }

    public boolean getIsTrueAnwser(int index) {
        return answers.get(index).isTrue();
    }

    public String getImgUrl() {
        return URLBase + this.img + ".jpg?alt=media";
    }

    public AnswerTestEntity getAnswer(int index) {
        return answers.get(index);
    }
}
