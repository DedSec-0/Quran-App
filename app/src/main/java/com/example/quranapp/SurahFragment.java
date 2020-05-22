package com.example.quranapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SurahFragment extends Fragment {

    private ArrayList <Surah> surahArrayList;
    private RecyclerView mRecyclerView;
    private SurahAdapter surahAdapter;


    public SurahFragment() {
        // Required empty public constructor
    }

    public static SurahFragment newInstance() {
        return new SurahFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        surahArrayList = getSurahArrayList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_surah, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_surah_view);
        surahAdapter = new SurahAdapter(surahArrayList, getActivity());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView.setAdapter(surahAdapter);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        surahAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Surah surah = (Surah) surahAdapter.getItem(position);

                int surah_id = surah.getId(); //mRecyclerView.getAdapter().getItemId(position);
                int ayah_number = surah.getAyahNumber();
                String surah_name = surah.getNameTranslate();

                Log.d("SurahFragment", "ID: " + surah_id + " Surah Name: " + surah_name);

                Bundle dataBundle = new Bundle();
                dataBundle.putInt(SurahDataSource.SURAH_ID_TAG, surah_id);
                dataBundle.putInt(SurahDataSource.SURAH_AYAH_NUMBER, ayah_number);
                dataBundle.putString(SurahDataSource.SURAH_NAME_TRANSLATE, surah_name);
                dataBundle.putBoolean(SurahDataSource.LIMIT_AYAHS, false);

                Intent intent = new Intent(getActivity(), AyahWordActivity.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });


    }

    private ArrayList <Surah> getSurahArrayList() {
        ArrayList <Surah> surahArrayList;
        SurahDataSource surahDataSource = new SurahDataSource(getActivity());
        surahArrayList = surahDataSource.getEnglishSurahArrayList();

        return surahArrayList;
    }


}