package com.example.quranapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;

public class formActivity extends AppCompatActivity {

    Spinner surah;
    ElegantNumberButton ayahsSt;
    ElegantNumberButton ayahsEn;
    Integer ayahSt;
    Integer ayahEn;
    Button btnSubmit;
    EditText namePerson;

    ArrayList <String> surahs = new ArrayList<>();
    ArrayList <Surah> surahArrayList;
    Surah surahSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        surah = findViewById(R.id.surah);
        ayahsSt = findViewById(R.id.ayahNumBtnSt);
        ayahsEn = findViewById(R.id.ayahNumBtnEn);
        btnSubmit = findViewById(R.id.submit);
        namePerson = findViewById(R.id.nameOfPerson);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSurahs();
        ArrayAdapter adapter = new ArrayAdapter<>(formActivity.this, android.R.layout.simple_spinner_dropdown_item, surahs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        surah.setAdapter(adapter);
        handleListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.toString().equals("Log Out"))
            startActivity(new Intent(formActivity.this, loginActivity.class));

        return super.onOptionsItemSelected(item);
    }

    void getSurahs() {
        SurahDataSource surahDataSource = new SurahDataSource(formActivity.this);
        surahArrayList = surahDataSource.getEnglishSurahArrayList();

        for (Surah singleSurah : surahArrayList)
            surahs.add(singleSurah.getNameTranslate());
    }

    void handleListeners() {

        //Surah Listener
        surah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(21);
                ((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(45, 45, 45));

                surahSelected = surahArrayList.get(position);
                ayahsSt.setNumber("1");
                ayahSt = 1;
                ayahsEn.setNumber("2");
                ayahEn = 2;
                ayahsSt.setRange(1, surahSelected.getAyahNumber());
                ayahsEn.setRange(2, surahSelected.getAyahNumber());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Ayahs Listener
        ayahsSt.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                ayahSt = Integer.parseInt(ayahsSt.getNumber());
            }
        });

        ayahsEn.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                ayahEn = Integer.parseInt(ayahsEn.getNumber());
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDataSource userDbRef = new userDataSource(formActivity.this);
                int surah_id = surahSelected.getId();
                int ayah_number = surahSelected.getAyahNumber();
                String surah_name = surahSelected.getNameTranslate();
                String userEmail = getIntent().getStringExtra("email");

                if (namePerson.getText().toString().equals("")) {
                    namePerson.setError("Enter Persons Name");
                    namePerson.requestFocus();
                    userDbRef = null;
                    return;
                }

                if (!userDbRef.checkForAyahs(ayahSt, ayahEn, surah_id)) {
                    Toast.makeText(formActivity.this, "Someone is already reading it", Toast.LENGTH_SHORT).show();
                    userDbRef = null;
                    return;
                }

                Bundle dataBundle = new Bundle();
                dataBundle.putInt(SurahDataSource.SURAH_ID_TAG, surah_id);
                dataBundle.putInt(SurahDataSource.SURAH_AYAH_NUMBER, ayah_number);
                dataBundle.putInt(SurahDataSource.STARTING_AYAH, ayahSt);
                dataBundle.putInt(SurahDataSource.ENDING_AYAH, ayahEn);
                dataBundle.putString(SurahDataSource.SURAH_NAME_TRANSLATE, surah_name);
                dataBundle.putBoolean(SurahDataSource.LIMIT_AYAHS, true);
                dataBundle.putString("email", userEmail);

                boolean added = userDbRef.addKhatam(userEmail, namePerson.getText().toString(), surah_id, ayahSt, ayahEn);
                if (added) {
                    Intent intent = new Intent(formActivity.this, AyahWordActivity.class);
                    intent.putExtras(dataBundle);
                    startActivity(intent);
                } else
                    Toast.makeText(formActivity.this, "Can't Submit, Try Again", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
