package miw.fellowshipfungi.models.profile;

import java.util.Map;

import miw.fellowshipfungi.models.EnconterEntity;
import miw.fellowshipfungi.models.ask.recognitionmodels.MusshroomEntity;
import miw.fellowshipfungi.models.ask.recognitionmodels.RecognitionEntity;

public class EnconterCollectionEntity extends EnconterEntity {
    private String enconterID;
    private MusshroomEntity musshroomEntity;
    private final String URLBase = "https://firebasestorage.googleapis.com/v0/b/fellowship-fungi.appspot.com/o/EncontersImg%2F";

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
        if (this.getNameImg()==null){
            return musshroomEntity.getImgUrl();
        }else{
            return URLBase+this.getNameImg()+"?alt=media";

        }

    }

    public String getEnconterID() {
        return enconterID;
    }
}
