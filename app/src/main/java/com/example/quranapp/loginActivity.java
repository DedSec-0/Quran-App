package com.example.quranapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class loginActivity extends AppCompatActivity {
    Button signInBtn;
    TextView RegBtn;
    EditText email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInBtn = findViewById(R.id.signIn);
        RegBtn = findViewById(R.id.notRegistered);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login Here");

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPass = pass.getText().toString();
                userDataSource userDbRef = new userDataSource(loginActivity.this);

                if(userEmail.equals("") || userPass.equals(""))
                    Toast.makeText(getApplicationContext(), "Fields are Empty", Toast.LENGTH_LONG).show();
                else {
                    if(userDbRef.checkCred(userEmail, userPass)) {
                            Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                            Khatam user = userDbRef.checkKhtamahUser(userEmail);

                            if(user == null){
                                Intent intent = new Intent(loginActivity.this, formActivity.class);
                                intent.putExtra("email", userEmail);
                                startActivity(intent);
                            }
                            else {
                                Bundle dataBundle = new Bundle();
                                dataBundle.putInt(SurahDataSource.SURAH_ID_TAG, user.getSurahId());
                                dataBundle.putInt(SurahDataSource.STARTING_AYAH, user.getStartingAyah());
                                dataBundle.putInt(SurahDataSource.ENDING_AYAH, user.getEndingAyah());
                                dataBundle.putBoolean(SurahDataSource.LIMIT_AYAHS, true);
                                dataBundle.putString("email", userEmail);

                                Intent intent = new Intent(loginActivity.this, AyahWordActivity.class);
                                intent.putExtras(dataBundle);
                                startActivity(intent);
                            }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Invalid Email of password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( loginActivity.this, registerActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent( loginActivity.this, MainActivity.class));

        return super.onOptionsItemSelected(item);
    }
}
