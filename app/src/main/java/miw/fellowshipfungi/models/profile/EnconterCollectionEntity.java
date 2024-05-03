package miw.fellowshipfungi.models.profile;

import java.util.Map;

import miw.fellowshipfungi.models.EnconterEntity;
import miw.fellowshipfungi.models.ask.recognitionmodels.MusshroomEntity;
import miw.fellowshipfungi.models.ask.recognitionmodels.RecognitionEntity;

public class EnconterCollectionEntity extends EnconterEntity {
    private MusshroomEntity musshroomEntity;

    public EnconterCollectionEntity(Map<String, Object> dataMapEnconter, RecognitionEntity recognitionEntity) {

        super((String) dataMapEnconter.get("speciedId"));
        super.setDate((String) dataMapEnconter.get("date"));
        super.setLocation((String) dataMapEnconter.get("location"));
        super.setWeather((String) dataMapEnconter.get("weather"));

        this.musshroomEntity = recognitionEntity.getMusshroomEntity();
    }

    public String getNameMussroom() {
        return musshroomEntity.getName();
    }

    public String getImgUrl() {
        return musshroomEntity.getImgUrl();
    }

}
