package miw.fellowshipfungi.models.profile;

import java.util.Map;

import miw.fellowshipfungi.models.EncounterEntity;
import miw.fellowshipfungi.models.ask.recognitionmodels.MusshroomEntity;
import miw.fellowshipfungi.models.ask.recognitionmodels.RecognitionEntity;

public class EncounterCollectionEntity extends EncounterEntity {

    private String encounterID;
    private MusshroomEntity musshroomEntity;

    public EncounterCollectionEntity(String encounterID, Map<String, Object> dataMapEncounter, RecognitionEntity recognitionEntity) {

        super((String) dataMapEncounter.get("speciedId"));
        this.setDate((String) dataMapEncounter.get("date"));
        this.setLocation((String) dataMapEncounter.get("location"));
        this.setWeather((String) dataMapEncounter.get("weather"));
        this.setImg((String) dataMapEncounter.get("nameImg"));

        this.encounterID = encounterID;
        this.musshroomEntity = recognitionEntity.getMusshroomEntity();
    }

    public String getNameMussroom() {
        return musshroomEntity.getName();
    }

    public String getImgUrl() {
        if (!this.hasImg()) {
            return musshroomEntity.getImgUrl();
        } else {
            android.util.Log.w("ddd___", super.getImgUrl());
            return super.getImgUrl();
        }

    }

    public String getEncounterID() {
        return encounterID;
    }
}
