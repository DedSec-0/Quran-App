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

public class registerActivity extends AppCompatActivity {

    Button registerBtn;
    TextView loginBtn;
    EditText email, pass, name, country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerBtn = findViewById(R.id.signIn);
        loginBtn = findViewById(R.id.alreadyRegistered);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        name = findViewById(R.id.name);
        country = findViewById(R.id.country);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register Here");

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = name.getText().toString();
                String userCountry = country.getText().toString();
                String userEmail = email.getText().toString();
                String userPass = pass.getText().toString();
                userDataSource userDbRef = new userDataSource(registerActivity.this);

                if(userName.equals("") || userEmail.equals("") || userPass.equals(""))
                    Toast.makeText(getApplicationContext(), "Fields are Empty", Toast.LENGTH_SHORT).show();
                else {
                    if(userDbRef.checkEmail(userEmail)) {
                        boolean inserted = userDbRef.regUser(userEmail, userPass, userName, userCountry);
                        if(inserted) {
                            startActivity(new Intent(registerActivity.this, formActivity.class));
                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "User with this email already exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( registerActivity.this, loginActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent( registerActivity.this, MainActivity.class));

        return super.onOptionsItemSelected(item);
    }
}
