package miw.fellowshipfungi.models.profile;

import java.util.Map;

import miw.fellowshipfungi.models.EnconterEntity;
import miw.fellowshipfungi.models.ask.recognitionmodels.MusshroomEntity;
import miw.fellowshipfungi.models.ask.recognitionmodels.RecognitionEntity;

public class EnconterCollectionEntity extends EnconterEntity {

    private String enconterID;
    private MusshroomEntity musshroomEntity;

    public EnconterCollectionEntity(String enconterID, Map<String, Object> dataMapEnconter, RecognitionEntity recognitionEntity) {

        super((String) dataMapEnconter.get("speciedId"));
        this.setDate((String) dataMapEnconter.get("date"));
        this.setLocation((String) dataMapEnconter.get("location"));
        this.setWeather((String) dataMapEnconter.get("weather"));
        this.setNameImg((String) dataMapEnconter.get("nameImg"));

        this.enconterID = enconterID;
        this.musshroomEntity = recognitionEntity.getMusshroomEntity();
    }

    public String getNameMussroom() {
        return musshroomEntity.getName();
    }

    public String getImgUrl() {
        if (!this.hasImg()) {
            return musshroomEntity.getImgUrl();
        } else {
            android.util.Log.w("ddd___",super.getImgUrl());
            return super.getImgUrl();
        }

    }

    public String getEnconterID() {
        return enconterID;
    }
}
