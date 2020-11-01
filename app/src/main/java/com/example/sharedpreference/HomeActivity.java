package com.example.sharedpreference;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class HomeActivity extends AppCompatActivity {

    private ImageView IvProfilePhoto;
    private Bitmap ImagePath;
    private SharedPreferences prefManager;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        prefManager = getApplicationContext().getSharedPreferences("DGaming", MODE_PRIVATE);
        editor = prefManager.edit();

        TextView TvUserName = findViewById(R.id.tvLoggedInName);
        TextView TvPhoneNo = findViewById(R.id.tvLoggedInPhoneNo);
        TextView TvDOB = findViewById(R.id.tvLoggedInDOB);
        IvProfilePhoto = findViewById(R.id.ivProfilePhoto);

        Bundle UserData = getIntent().getExtras();

        Button BtnEdit = findViewById(R.id.btnEdit);

        BtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, EditActivity.class));
                finish();
            }
        });

        if(UserData != null){
            String UserName = UserData.getString("USER_NAME");
            String PhoneNo = UserData.getString("PHONE_NO");
            String UserDOB = UserData.getString("USER_DOB");

            TvUserName.setText(UserName);
            TvPhoneNo.setText(PhoneNo);
            TvDOB.setText(UserDOB);
        }
        IvProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(CameraIntent, 919);
            }
        });

        String RetrievedImageValue = prefManager.getString("IMAGE_BYTES", "");
        if(!RetrievedImageValue.isEmpty()){
            byte[] ImageToDisplayBytes = RetrievedImageValue.getBytes();

            Bitmap ImageBitmap = BitmapFactory.decodeByteArray(ImageToDisplayBytes, 0, ImageToDisplayBytes.length);
            IvProfilePhoto.setImageBitmap(ImageBitmap);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 919){
            if(data.getExtras() != null){
                ImagePath = (Bitmap) data.getExtras().get("data");
            }else {
                try {
                    ImagePath = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            IvProfilePhoto.setImageBitmap(ImagePath);

            ByteArrayOutputStream Stream = new ByteArrayOutputStream();
            ImagePath.compress(Bitmap.CompressFormat.PNG, 100, Stream);
            byte[] ImageBytes = Stream.toByteArray();
            String ImageByteStringValue =new String(ImageBytes);
            editor.putString("IMAGE_BYTES", ImageByteStringValue);
            editor.commit();
        }
    }
}
