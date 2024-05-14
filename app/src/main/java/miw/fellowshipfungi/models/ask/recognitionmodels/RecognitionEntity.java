package miw.fellowshipfungi.models.ask.recognitionmodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecognitionEntity {
    private AskEntity askEntity;
    private List<AnswerEntity> answerEntities;

    private MushroomEntity mushroomEntity;

    public RecognitionEntity() {
        this.answerEntities = new ArrayList<AnswerEntity>();
    }

    public void addAnswer(Map<String, Object> dataMap) {
        this.answerEntities.add(new AnswerEntity(dataMap));
    }

    public String getAskText() {
        return askEntity.getText();
    }

    public void setAskEntity(Map<String, Object> dataMap) {
        this.askEntity = new AskEntity(dataMap);
    }

    public List<AnswerEntity> getAnswerEntities() {
        return answerEntities;
    }

    public MushroomEntity getMushroomEntity() {
        return mushroomEntity;
    }

    public void setMushroomEntity(Map<String, Object> dataMap) {
        this.mushroomEntity = new MushroomEntity(dataMap);
    }

    public String getMushroomName() {
        return this.mushroomEntity.getName();
    }

    public String getMushroomImgUrl() {
        return this.mushroomEntity.getImgUrl();
    }

    public String getMushroomId() {
        return this.mushroomEntity.getId();
    }


}
