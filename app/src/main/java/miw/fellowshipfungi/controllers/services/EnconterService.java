package miw.fellowshipfungi.controllers.services;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import miw.fellowshipfungi.models.EnconterEntity;

public class EnconterService extends BaseService {

    private static EnconterService instance;
    private final static String folder="EncontersImg/";

    private EnconterService() {
        super();
    }

    public static EnconterService getInstance() {
        if (instance == null) {
            instance = new EnconterService();
        }
        return instance;
    }

    public void saveEnconter(EnconterEntity enconterEntity, Uri imageUri) {
        if (imageUri != null) {
            String nameImg = generateNameImg();
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(this.folder + nameImg);
            UploadTask uploadTask = storageRef.putFile(imageUri);

            uploadTask.addOnSuccessListener(taskSnapshot -> {

                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    enconterEntity.setNameImg(nameImg);
                    saveEnconter(enconterEntity);
                });
            });
        } else {
            saveEnconter(enconterEntity);
        }
    }

    private void saveEnconter(EnconterEntity enconterEntity) {
      this.getProfileDocument()
                .collection(COLLECTION_ENCONTERS)
                .add(enconterEntity.getMap());
    }

    private String generateNameImg() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String formattedDate = dateFormat.format(new Date());
        return "enconter_" + getUserId() + "_" + formattedDate + ".jpg";
    }
}
