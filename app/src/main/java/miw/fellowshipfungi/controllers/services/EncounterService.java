package miw.fellowshipfungi.controllers.services;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import miw.fellowshipfungi.models.EncounterEntity;

public class EncounterService extends BaseService {

    private static EncounterService instance;
    private final static String folder="EncountersImg/";

    private EncounterService() {
        super();
    }

    public static EncounterService getInstance() {
        if (instance == null) {
            instance = new EncounterService();
        }
        return instance;
    }

    public void saveEncounter(EncounterEntity encounterEntity, Uri imageUri) {
        if (imageUri != null) {
            String nameImg = generateNameImg();
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(this.folder + nameImg);
            UploadTask uploadTask = storageRef.putFile(imageUri);

            uploadTask.addOnSuccessListener(taskSnapshot -> {

                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    encounterEntity.setImg(nameImg);
                    saveEncounter(encounterEntity);
                });
            });
        } else {
            saveEncounter(encounterEntity);
        }
    }

    private void saveEncounter(EncounterEntity encounterEntity) {
      this.getProfileDocument()
                .collection(COLLECTION_ENCOUNTERS)
                .add(encounterEntity.getMap());
    }

    private String generateNameImg() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String formattedDate = dateFormat.format(new Date());
        return "encounter_" + getUserId() + "_" + formattedDate + ".jpg";
    }
}
