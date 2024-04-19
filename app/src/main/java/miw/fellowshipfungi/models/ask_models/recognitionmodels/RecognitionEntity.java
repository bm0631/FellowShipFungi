package miw.fellowshipfungi.models.ask_models.recognitionmodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecognitionEntity {
    private AskEntity askEntity;
    private List<AnswerEntity> answerEntities;

    private MusshroomEntity musshroomEntity;

    public RecognitionEntity() {
        this.answerEntities = new ArrayList<AnswerEntity>();
    }

    public void addAnswer(Map<String, Object> dataMap) {
        this.answerEntities.add(new AnswerEntity(dataMap));
    }

    public AskEntity getAskEntity() {
        return askEntity;
    }

    public void setAskEntity(Map<String, Object> dataMap) {
        this.askEntity = new AskEntity(dataMap);
    }

    public List<AnswerEntity> getAnswerEntities() {
        return answerEntities;
    }

    public MusshroomEntity getMusshroomEntity() {
        return musshroomEntity;
    }

    public void setMusshroomEntity(Map<String, Object> dataMap) {
        this.musshroomEntity = new MusshroomEntity(dataMap);
    }

    @Override
    public String toString() {
        return "RecognitionEntity{" +
                "askEntity=" + askEntity +
                ", answerEntities=" + answerEntities +
                ", musshroomEntity=" + musshroomEntity +
                '}';
    }
}
