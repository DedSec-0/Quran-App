package com.example.quranapp;

import java.util.ArrayList;

public class AyahWord {
    private ArrayList <Word> word;
    private String quranArabic;
    private String quranTranslate;
    private Integer quranVerseId;

    public ArrayList <Word> getWord() {
        return word;
    }

    public void setWord(ArrayList <Word> word) {
        this.word = word;
    }

    public String getQuranArabic() {
        return quranArabic;
    }

    public void setQuranArabic(String quranArabic) {
        this.quranArabic = quranArabic;
    }

    public String getQuranTranslate() {
        return quranTranslate;
    }

    public void setQuranTranslate(String quranTranslate) {
        this.quranTranslate = quranTranslate;
    }

    public Integer getQuranVerseId() {
        return quranVerseId;
    }

    public void setQuranVerseId(Integer quranVerseId) {
        this.quranVerseId = quranVerseId;
    }
}
