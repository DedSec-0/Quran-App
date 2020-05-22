package com.example.quranapp;

public class Surah {
    private Integer id;
    private Integer no;
    private String nameEnglish;
    private String nameArabic;
    private String nameTranslate;

    private String meanEnglish;
    private Integer ayahNumber;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getNameArabic() {
        return nameArabic;
    }

    public void setNameArabic(String nameArabic) {
        this.nameArabic = nameArabic;
    }

    public String getNameTranslate() {
        return nameTranslate;
    }

    public void setNameTranslate(String nameTranslate) {
        this.nameTranslate = nameTranslate;
    }

    public String getMeanEnglish() {
        return meanEnglish;
    }

    public void setMeanEnglish(String meanEnglish) {
        this.meanEnglish = meanEnglish;
    }

    public Integer getAyahNumber() {
        return ayahNumber;
    }

    public void setAyahNumber(Integer ayahNumber) {
        this.ayahNumber = ayahNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
