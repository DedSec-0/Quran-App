package com.example.quranapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
public class SurahDataSource {

    public final static String SURAH_TABLE_NAME = "surah_name";
    public final static String SURAH_ID = "_id";
    public final static String SURAH_ID_TAG = "surah_id";
    public final static String SURAH_NO = "surah_no";
    public final static String SURAH_NAME_ARABIC = "name_arabic";
    public final static String SURAH_NAME_ENGLISH = "name_english";
    public final static String SURAH_NAME_TRANSLATE = "name_translate";
    public final static String SURAH_AYAH_NUMBER = "ayah_number";
    public final static String LIMIT_AYAHS = "Limit";
    public final static String SURAH_TYPE = "type";
    public final static String STARTING_AYAH = "AyahSt";
    public final static String ENDING_AYAH = "AyahEn";
    private static Cursor cursor;
    private DatabaseHelper databaseHelper;

    public SurahDataSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public ArrayList <Surah> getEnglishSurahArrayList() {

        ArrayList <Surah> surahArrayList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT surah_name._id,surah_name.name_arabic,surah_name.name_english,surah_name.ayah_number FROM surah_name", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Surah surah = new Surah();
            surah.setId(cursor.getInt(cursor.getColumnIndex(SURAH_ID)));
            surah.setNameArabic(cursor.getString(cursor.getColumnIndex(SURAH_NAME_ARABIC)));
            surah.setNameTranslate(cursor.getString(cursor.getColumnIndex(SURAH_NAME_ENGLISH)));
            surah.setAyahNumber(cursor.getInt(cursor.getColumnIndex(SURAH_AYAH_NUMBER)));
            surahArrayList.add(surah);
            cursor.moveToNext();

        }
        cursor.close();
        db.close();

        return surahArrayList;
    }

}
