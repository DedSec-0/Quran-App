package com.example.quranapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import java.util.ArrayList;
import java.util.Objects;


public class AyahWordFragment extends Fragment {


    private int surah_id;
    private ArrayList <AyahWord> ayahWordArrayList;
    private RecyclerView mRecyclerView;
    private AyahWordAdapter ayahWordAdapter;

    private AyahWordFragment() {
        // Required empty public constructor
    }

    static AyahWordFragment newInstance(Bundle bundle) {
        AyahWordFragment ayahWordFragment = new AyahWordFragment();
        ayahWordFragment.setArguments(bundle);

        return ayahWordFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        surah_id = getArguments().getInt(SurahDataSource.SURAH_ID_TAG);

        boolean limitAyahs = getArguments().getBoolean(SurahDataSource.LIMIT_AYAHS, false);
        int ayah_number = getArguments().getInt(SurahDataSource.SURAH_AYAH_NUMBER, 1);
        int ayahSt = getArguments().getInt(SurahDataSource.STARTING_AYAH, 0);
        int ayahEn = getArguments().getInt(SurahDataSource.ENDING_AYAH, 0);

        if(limitAyahs)
            ayahWordArrayList = getAyahWordsBySurah_Range(surah_id, ayahSt, ayahEn);
        else
            ayahWordArrayList = getAyahWordsBySurah(surah_id, ayah_number);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ayah_word, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_ayah_word_view);
        ayahWordAdapter = new AyahWordAdapter(ayahWordArrayList, Objects.requireNonNull(getActivity()), surah_id);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setAdapter(ayahWordAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setVerticalScrollBarEnabled(true);


        //set headerview
        RecyclerViewHeader recyclerViewHeader = view.findViewById(R.id.header);
        TextView headerTextView = recyclerViewHeader.findViewById(R.id.headerTextView);
        headerTextView.setText(getString(R.string.bismillah));
        recyclerViewHeader.attachTo(mRecyclerView, true);

    }

    ArrayList <AyahWord> getAyahWordsBySurah(long surah_id, long ayah_number) {
        ArrayList <AyahWord> ayahWordArrayList;
        AyahWordDataSource ayahWordDataSource = new AyahWordDataSource(getActivity());
        ayahWordArrayList = ayahWordDataSource.getEnglishAyahWordsBySurah(surah_id, ayah_number);

        return ayahWordArrayList;
    }

    ArrayList <AyahWord> getAyahWordsBySurah_Range(int surah_id, int stAyah, int enAyah) {
        ArrayList <AyahWord> ayahWordArrayList;
        AyahWordDataSource ayahWordDataSource = new AyahWordDataSource(getActivity());
        ayahWordArrayList = ayahWordDataSource.getEnglishAyahWordsBySurah_Range(surah_id, stAyah, enAyah);

        return ayahWordArrayList;
    }




}