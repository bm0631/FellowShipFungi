package miw.fellowshipfungi.controllers.services;

import android.net.Uri;
import android.widget.Toast;

import miw.fellowshipfungi.controllers.CreateEnconterActivity;
import miw.fellowshipfungi.models.EnconterEntity;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EnconterService extends BaseService {

    private static EnconterService instance;


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
            String nameImg="enconter_" + getUserId() +"_"+ new Date()+".jpg";

            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("EncontersImg/"+nameImg);
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
        db.collection(COLLECTION_PROFILE)
                .document(getUserId())
                .collection(COLLECTION_ENCONTERS)
                .add(enconterEntity.getMap());
    }
}
