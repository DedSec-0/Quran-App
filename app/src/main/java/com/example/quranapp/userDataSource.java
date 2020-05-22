package com.example.quranapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

class userDataSource {

    private DatabaseHelper databaseHelper;
    public final static String USER_EMAIL = "email";
    public final static String USER_PASSWORD = "password";
    public final static String USER_NAME = "name";
    public final static String USER_COUNTRY = "country";
    public final static String KHTAMAH_PERSON_NAME = "NamePerson";
    public final static String SURAH_ID = "surahId";
    public final static String STARTING_AYAH = "StartingAyah";
    public final static String ENDING_AYAH = "EndingAyah";
    public final static String KHTAMAH_STARTED = "Started";

    userDataSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    boolean regUser(String email, String pass, String name, String country) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_EMAIL, email);
        contentValues.put(USER_PASSWORD, pass);
        contentValues.put(USER_NAME, name);
        contentValues.put(USER_COUNTRY, country);

        long insert = db.insert("user", null, contentValues);
        db.close();
        if(insert == -1)
            return false;

        return true;
    }

    boolean checkEmail(String email) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * from user WHERE email=?", new String[]{email});
        if(cursor.getCount() > 0) {
            cursor.close();
            return false;
        }

        db.close();
        cursor.close();
        return true;
    }

    boolean checkCred(String email, String pass) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * from user WHERE email=? AND password=?", new String[]{email, pass});
        if(cursor.getCount() > 0){
            cursor.close();
            return true;
        }

        db.close();
        cursor.close();
        return false;
    }

    boolean addKhatam(String email, String personName, int surahId, int ayahSt, int ayahEn) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        SQLiteDatabase dbR = databaseHelper.getReadableDatabase();

        Cursor cursor = dbR.rawQuery("Select * from khtamRecord WHERE NamePerson=?", new String[]{personName});
        if(cursor.getCount() == 0){
            ContentValues conValues = new ContentValues();
            conValues.put(KHTAMAH_PERSON_NAME, personName);
            db.insert("khtamRecord", null, conValues);
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_EMAIL, email);
        contentValues.put(KHTAMAH_PERSON_NAME, personName);
        contentValues.put(SURAH_ID, surahId);
        contentValues.put(STARTING_AYAH, ayahSt);
        contentValues.put(ENDING_AYAH, ayahEn);

        long insert = db.insert("khatam_participation", null, contentValues);
        db.close();
        if(insert == -1)
            return false;

        return true;
    }

    boolean getKhtamStatus(String email) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String namePerson = getPersonName(email);
        Cursor cursor = db.rawQuery("SELECT * from khtamRecord WHERE NamePerson=?", new String[]{namePerson});
        cursor.moveToFirst();
        if(cursor.getCount() == 0)
            return false;

        int status = cursor.getInt(cursor.getColumnIndex(KHTAMAH_STARTED));

        cursor.close();
        if(status == 0)
            return false;

        return true;
    }

    void khtamStarted(String personName) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from khtamRecord WHERE NamePerson=?", new String[]{personName});
        if(cursor.getCount() > 0){
            ContentValues conValues = new ContentValues();
            conValues.put(KHTAMAH_STARTED, 1);
            db.update("khtamRecord", conValues, "NamePerson=?", new String[]{personName});
        }

        cursor.close();
    }

    void khtamCompleted(String email) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String namePerson = getPersonName(email);
        int subs = getSubscribers(email);

        db.execSQL("DELETE FROM khatam_participation WHERE email=?", new String[]{email});
        if(subs == 0){
            //Khtamah is ended
            db.execSQL("DELETE FROM khtamRecord WHERE NamePerson=?", new String[]{namePerson});
        }
    }

    String getPersonName(String email) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * from khatam_participation WHERE email=?", new String[]{email});
        cursor.moveToFirst();
        if(cursor.getCount() == 0)
            return null;

        String namePerson = cursor.getString(cursor.getColumnIndex(KHTAMAH_PERSON_NAME));
        cursor.close();

        return namePerson;
    }

    Khatam checkKhtamahUser(String email) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * from khatam_participation WHERE email=?", new String[]{email});
        cursor.moveToFirst();

        Khatam khtamahUser = null;

        if(cursor.getCount() > 0) {
            khtamahUser = new Khatam();
            khtamahUser.setEmail(cursor.getString(cursor.getColumnIndex(USER_EMAIL)));
            khtamahUser.setNamePerson(cursor.getString(cursor.getColumnIndex(KHTAMAH_PERSON_NAME)));
            khtamahUser.setSurahId(cursor.getInt(cursor.getColumnIndex(SURAH_ID)));
            khtamahUser.setStartingAyah(cursor.getInt(cursor.getColumnIndex(STARTING_AYAH)));
            khtamahUser.setEndingAyah(cursor.getInt(cursor.getColumnIndex(ENDING_AYAH)));
        }

        db.close();
        cursor.close();
        return khtamahUser;
    }

    boolean checkForAyahs(int ayahSt, int ayahEn, int surahId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT StartingAyah, EndingAyah from khatam_participation WHERE surahId=" + surahId, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int StartingAyah = cursor.getInt(cursor.getColumnIndex(STARTING_AYAH));
            int EndingAyah = cursor.getInt(cursor.getColumnIndex(ENDING_AYAH));

            if(!(((ayahSt < StartingAyah && ayahEn < StartingAyah) && (ayahSt < EndingAyah && ayahEn < EndingAyah)) || ((ayahSt > StartingAyah && ayahEn > StartingAyah) && (ayahSt > EndingAyah && ayahEn > EndingAyah)))) {
                cursor.close();
                return false;
            }
            if(cursor.isLast())
                break;

            cursor.moveToNext();
        }

        cursor.close();
        return true;
    }

    int getSubscribers(String email) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String namePerson = getPersonName(email);
        if(namePerson == null)
            return 0;

        Cursor cursor = db.rawQuery("SELECT * from khatam_participation WHERE NamePerson=?", new String[]{namePerson});
        int count = cursor.getCount();
        cursor.close();

        if(count >= 2)
            khtamStarted(namePerson);

        return count;
    }
}
