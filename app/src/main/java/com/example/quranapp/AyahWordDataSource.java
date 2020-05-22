package com.example.quranapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class AyahWordDataSource {
    //SELECT bywords._id,bywords.surah_id,bywords.verse_id,bywords.words_id,bywords.words_ar,bywords.translate_en FROM bywords where bywords.surah_id = 1

    public final static String AYAHWORD_TABLE_NAME = "bywords";
    public final static String AYAHWORD_WORDS_TRANSLATE_EN = "translate_en";
    public final static String AYAHWORD_ID = "_id";
    public final static String AYAHWORD_ID_TAG = "ayah_word_id";
    public final static String AYAHWORD_SURAH_ID = "surah_id";
    public final static String AYAHWORD_VERSE_ID = "verse_id";
    public final static String AYAHWORD_WORDS_ID = "words_id";
    public final static String AYAHWORD_WORDS_AR = "words_ar";

    public final static String AYAHWORD_TABLE_NAME_ENGLISH = "bywords";


    public final static String QURAN_TABLE = "quran";
    public final static String QURAN_ENGLSIH = "english";
    private final static String QURAN_VERSE_ID = "verse_id";
    private final static String QURAN_ARABIC = "arabic";

    private static Cursor cursor;
    private static Cursor quranCursor;


    private DatabaseHelper databaseHelper;

    AyahWordDataSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public ArrayList <AyahWord> getEnglishAyahWordsBySurah(long surah_id, long ayah_number){
        long tempVerseWord;
        long tempVerseQuran;
        ArrayList<AyahWord> ayahWordArrayList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT bywords._id,bywords.surah_id,bywords.verse_id,bywords.words_id,bywords.words_ar,bywords.translate_en FROM bywords where bywords.surah_id = " + surah_id, null);
        cursor.moveToFirst();

        quranCursor = db.rawQuery("SELECT quran.verse_id,quran.arabic,quran.english from quran WHERE quran.surah_id = " + surah_id, null);
        quranCursor.moveToFirst();

        for (long i = 1; i <= ayah_number; i++) {
            tempVerseWord = i;
            tempVerseQuran = i;

            AyahWord ayahWord = new AyahWord();
            ArrayList <Word> wordArrayList = new ArrayList<>();

            while (i == tempVerseWord && !cursor.isAfterLast()) {
                tempVerseWord = cursor.getLong(cursor.getColumnIndex(AYAHWORD_VERSE_ID));
                if (tempVerseWord != i)
                    continue;

                Word word = new Word();
                word.setVerseId(cursor.getLong(cursor.getColumnIndex(AYAHWORD_VERSE_ID)));
                word.setWordsId(cursor.getLong(cursor.getColumnIndex(AYAHWORD_WORDS_ID)));
                word.setWordsAr(cursor.getString(cursor.getColumnIndex(AYAHWORD_WORDS_AR)));
                word.setTranslate(cursor.getString(cursor.getColumnIndex(AYAHWORD_WORDS_TRANSLATE_EN)));
                wordArrayList.add(word);
                cursor.moveToNext();
            }


            while (i == tempVerseQuran && !quranCursor.isAfterLast()) {
                tempVerseQuran = quranCursor.getLong(quranCursor.getColumnIndex(QURAN_VERSE_ID));
                if (tempVerseQuran != i)
                    continue;

                ayahWord.setQuranVerseId(quranCursor.getInt(quranCursor.getColumnIndex(QURAN_VERSE_ID)));
                ayahWord.setQuranArabic(quranCursor.getString(quranCursor.getColumnIndex(QURAN_ARABIC)));
                ayahWord.setQuranTranslate(quranCursor.getString(quranCursor.getColumnIndex(QURAN_ENGLSIH)));
                quranCursor.moveToNext();
            }

            ayahWord.setWord(wordArrayList);
            ayahWordArrayList.add(ayahWord);
        }

        quranCursor.close();
        cursor.close();
        db.close();

        return ayahWordArrayList;
    }


    public ArrayList <AyahWord> getEnglishAyahWordsBySurahVerse(long surah_id, long ayah_number){
        ArrayList<AyahWord> ayahWordArrayList = new ArrayList<AyahWord>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        for (long i = 1; i <= ayah_number; i++) {

            cursor = db.rawQuery("SELECT bywords._id,bywords.surah_id,bywords.verse_id,bywords.words_id,bywords.words_ar," +
                    "bywords.translate_en FROM bywords where bywords.surah_id = " + surah_id + " AND bywords.verse_id = " + i, null);
            cursor.moveToFirst();

            AyahWord ayahWord = new AyahWord();
            ArrayList<Word> wordArrayList = new ArrayList<Word>();

            while (!cursor.isAfterLast()) {

                Word word = new Word();
                // word.setAyahWord_id(cursor.getLong(cursor.getColumnIndex(AYAHWORD_ID)));
                // word.setAyahWord_surah_id(cursor.getLong(cursor.getColumnIndex(AYAHWORD_SURAH_ID)));
                word.setVerseId(cursor.getLong(cursor.getColumnIndex(AYAHWORD_VERSE_ID)));
                word.setWordsId(cursor.getLong(cursor.getColumnIndex(AYAHWORD_WORDS_ID)));
                word.setWordsAr(cursor.getString(cursor.getColumnIndex(AYAHWORD_WORDS_AR)));
                word.setTranslate(cursor.getString(cursor.getColumnIndex(AYAHWORD_WORDS_TRANSLATE_EN)));
                // Log.d("AyahWordDataSource", "currentAyah: " + i + " " + cursor.getString(cursor.getColumnIndex(AYAHWORD_WORDS_TRANSLATE_EN)));
                wordArrayList.add(word);
                ayahWord.setWord(wordArrayList);
                cursor.moveToNext();
            }
            ayahWordArrayList.add(ayahWord);
        }


        cursor.close();
        db.close();
        return ayahWordArrayList;
    }


    public ArrayList <Word> getEnglishWordsBySurah(long surah_id, long ayah_number){
        ArrayList<Word> wordArrayList = new ArrayList<Word>();
        Word word;

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT bywords._id,bywords.surah_id,bywords.verse_id,bywords.words_id,bywords.words_ar," +
                "bywords.translate_en FROM bywords where bywords.surah_id = " + surah_id, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            word = new Word();
            word.setVerseId(cursor.getLong(cursor.getColumnIndex(AYAHWORD_VERSE_ID)));
            word.setWordsId(cursor.getLong(cursor.getColumnIndex(AYAHWORD_WORDS_ID)));
            word.setWordsAr(cursor.getString(cursor.getColumnIndex(AYAHWORD_WORDS_AR)));
            word.setTranslate(cursor.getString(cursor.getColumnIndex(AYAHWORD_WORDS_TRANSLATE_EN)));

            Log.d("AyahWordDataSource", "currentAyah: " + " " + cursor.getString(cursor.getColumnIndex(AYAHWORD_WORDS_TRANSLATE_EN)));
            wordArrayList.add(word);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return wordArrayList;
    }


    public ArrayList<AyahWord> getEnglishAyahWordsBySurah_Range(long surah_id, int stAyah, int enAyah) {
        int tempVerseQuran;
        ArrayList<AyahWord> ayahWordArrayList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        quranCursor = db.rawQuery("SELECT quran.verse_id,quran.arabic,quran.english from quran WHERE quran.surah_id = " + surah_id + " AND quran.verse_id >= "  + stAyah + " AND quran.verse_id <= " + enAyah, null);
        quranCursor.moveToFirst();

        for (int i = stAyah; i <= enAyah; i++) {
            tempVerseQuran = i;

            AyahWord ayahWord = new AyahWord();
            while (i == tempVerseQuran && !quranCursor.isAfterLast()) {
                tempVerseQuran = quranCursor.getInt(quranCursor.getColumnIndex(QURAN_VERSE_ID));
                if (tempVerseQuran != i)
                    continue;

                ayahWord.setQuranVerseId(quranCursor.getInt(quranCursor.getColumnIndex(QURAN_VERSE_ID)));
                ayahWord.setQuranArabic(quranCursor.getString(quranCursor.getColumnIndex(QURAN_ARABIC)));
                ayahWord.setQuranTranslate(quranCursor.getString(quranCursor.getColumnIndex(QURAN_ENGLSIH)));
                quranCursor.moveToNext();
            }

            ayahWordArrayList.add(ayahWord);
        }

        quranCursor.close();
        db.close();

        return ayahWordArrayList;
    }
}
