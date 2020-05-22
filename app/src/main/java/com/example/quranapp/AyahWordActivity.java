package com.example.quranapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

public class AyahWordActivity extends AppCompatActivity {

    static public String surahName;
    private Boolean flag;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayah_word);

        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        surahName = bundle.getString(SurahDataSource.SURAH_NAME_TRANSLATE, "Surah");
        flag = bundle.getBoolean(SurahDataSource.LIMIT_AYAHS, false);
        userEmail = bundle.getString("email");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(surahName);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, AyahWordFragment.newInstance(bundle))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(flag) {
            getMenuInflater().inflate(R.menu.menu, menu);

            userDataSource userDbRef = new userDataSource(AyahWordActivity.this);
            int subs = userDbRef.getSubscribers(userEmail);
            boolean khtemahStarted = userDbRef.getKhtamStatus(userEmail);
            if(subs >= 2 || khtemahStarted) {
                //Khatam Started
                if(subs >= 2)
                    displayNotification("Khatemah is started", this);
                menu.getItem(0).setVisible(true);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        if(item.toString().equals("Log Out") && flag)
            startActivity(new Intent(AyahWordActivity.this, loginActivity.class));
        else if(item.toString().equals("Done") && flag) {
            new userDataSource(AyahWordActivity.this).khtamCompleted(userEmail);
            startActivity(new Intent(AyahWordActivity.this, loginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public static void displayNotification(String msg, Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "khtamNotifications")
                .setSmallIcon(R.drawable.ic_book_notification)
                .setContentTitle("Khatemah")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentText(msg);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(001, builder.build());
    }
}
