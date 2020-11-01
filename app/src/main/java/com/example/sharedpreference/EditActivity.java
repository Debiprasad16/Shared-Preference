package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private EditText EtUserName;
    private EditText EtPhoneNo;
    private EditText EtDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        EtUserName = findViewById(R.id.etEditUserName);
        EtPhoneNo = findViewById(R.id.etEditPhoneNumber);
        EtDOB = findViewById(R.id.etDOB);

        Button BtnEditSave = findViewById(R.id.btnEditSave);
        Button BtnCancel = findViewById(R.id.btnCancel);

        BtnEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserName = EtUserName.getText().toString();
                String PhoneNo = EtPhoneNo.getText().toString();
                String DOB = EtDOB.getText().toString();

                if(UserName.isEmpty() || PhoneNo.isEmpty() || DOB.isEmpty()){
                    Toast.makeText(EditActivity.this, "These Fields can't be Empty", Toast.LENGTH_LONG).show();
                }else {
                    Intent EditIntent = new Intent(EditActivity.this, HomeActivity.class);
                    EditIntent.putExtra("USER_NAME", UserName);
                    EditIntent.putExtra("PHONE_NO", PhoneNo);
                    EditIntent.putExtra("USER_DOB", DOB);
                    startActivity(EditIntent);
                    finish();
                }
            }
        });
        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditActivity.this, "User Cancelled", Toast.LENGTH_LONG).show();
                startActivity(new Intent(EditActivity.this, HomeActivity.class));
                finish();
            }
        });
    }
}