package com.example.quranapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AyahWordAdapter extends RecyclerView.Adapter <AyahWordAdapter.AyahViewHolder> {

    public Context context;
    private int surah_id;
    private ArrayList<AyahWord> ayahWordArrayList;

    AyahWordAdapter(ArrayList<AyahWord> ayahWordArrayList, Context context, int surah_id) {

        this.ayahWordArrayList = ayahWordArrayList;
        this.context = context;
        this.surah_id = surah_id;
    }

    @Override
    public int getItemCount() {
        return ayahWordArrayList.size();
    }

    @Override
    public long getItemId(int position) {

        AyahWord ayahWord = ayahWordArrayList.get(position);
        long itemId = 1;

        for (Word word : ayahWord.getWord()) {
            itemId = word.getVerseId();
        }
        return itemId;
    }

    @NonNull
    @Override
    public AyahWordAdapter.AyahViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ayah_word, parent, false);

        return new AyahViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AyahWordAdapter.AyahViewHolder holder, int position) {

        final AyahWord ayahWord = ayahWordArrayList.get(position);

        holder.verse_idTextView.setText("(" + Long.toString(ayahWord.getQuranVerseId()) + ")");
        holder.arabic_textView.setText(ayahWord.getQuranArabic());
        holder.arabic_textView.setTextSize(30);
        holder.arabic_textView.setVisibility(View.VISIBLE);

        if (position % 2 == 0) {
            holder.verse_idTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.color2));
            holder.arabic_textView.setBackgroundColor(ContextCompat.getColor(context, R.color.color2));

        } else {
            holder.verse_idTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.color3));
            holder.arabic_textView.setBackgroundColor(ContextCompat.getColor(context, R.color.color3));
        }

    }

    static class AyahViewHolder extends RecyclerView.ViewHolder {

        TextView verse_idTextView;
        TextView arabic_textView;

        AyahViewHolder(View view) {
            super(view);
            verse_idTextView = view.findViewById(R.id.verse_id_textView);
            arabic_textView = view.findViewById(R.id.arabic_textView);
        }

    }
}
