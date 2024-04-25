package miw.fellowshipfungi.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.Random;

import miw.fellowshipfungi.R;

public class MainMenuActivity extends AppCompatActivity {
    final static String LOG_TAG = "Fellowship Fungi Main Menu";
    final static String ROOT_IMG_URL ="https://firebasestorage.googleapis.com/v0/b/fellowship-fungi.appspot.com/o/Tittle%2Ftitle_";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main_menu);
        this.loadTittleImg();
    }

    private void loadTittleImg() {
        ImageView mushroomImage = findViewById(R.id.logoImageView);
        String imageUrl = ROOT_IMG_URL + (new Random().nextInt(5) + 1)+".png?alt=media";
        Picasso.get().load(imageUrl).into(mushroomImage);
    }

    public void onRecognitionClick(View view){
        Log.i(LOG_TAG, "Se pulsa Nuevo Recognition Musshrom");
        Intent intent = new Intent(this, RecognitionActivity.class);
        intent.putExtra("Current", "Ask_001");
        intent.putExtra("countAsks", 1);
        startActivity(intent);
    }
    public void onOpenCuriosityClick(View view){
        //TODO
    }
    public void onOpenTestClick(View view){
        //TODO
    }
    public void onOpenProfileClick(View view){
        //TODO
    }
    public void onLoginClick(View view){
        //TODO
    }
    public void onLogoutClick(View view){
        //TODO
    }
}
