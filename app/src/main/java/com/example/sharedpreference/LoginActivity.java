package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefManager;
    private SharedPreferences.Editor editor;

    private EditText etEmailAddress;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefManager = getApplicationContext().getSharedPreferences("DGaming", MODE_PRIVATE);
        editor = prefManager.edit();

        etEmailAddress = findViewById(R.id.et_email_address);
        etPassword = findViewById(R.id.et_password);

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailInfo = etEmailAddress.getText().toString();
                String passwordInfo = etPassword.getText().toString();

                editor.putString("EMAIL_ID", emailInfo);
                editor.putString("PASSWORD", passwordInfo);
                editor.putBoolean("IS_LOGIN_SUCCESSFUL", true);

                editor.apply();
                login();
            }
        });
        boolean IsUserLoggedIn = prefManager.getBoolean("IS_LOGIN_SUCCESSFUL", false);

        if(IsUserLoggedIn){
            login();
        }
    }

    private void login(){
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }
}