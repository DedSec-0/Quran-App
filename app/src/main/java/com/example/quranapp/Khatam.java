package com.example.quranapp;

public class Khatam {
    private String email;
    private String NamePerson;
    private Integer SurahId;
    private Integer StartingAyah;
    private Integer EndingAyah;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNamePerson() {
        return NamePerson;
    }

    public void setNamePerson(String namePerson) {
        NamePerson = namePerson;
    }

    public Integer getSurahId() {
        return SurahId;
    }

    public void setSurahId(Integer surahId) {
        SurahId = surahId;
    }

    public Integer getStartingAyah() {
        return StartingAyah;
    }

    public void setStartingAyah(Integer startingAyah) {
        StartingAyah = startingAyah;
    }

    public Integer getEndingAyah() {
        return EndingAyah;
    }

    public void setEndingAyah(Integer endingAyah) {
        EndingAyah = endingAyah;
    }
}
