package com.example.eapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eapp.R;
import com.example.eapp.models.Word;

import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {

    private List<Word> wordList;
    private Context context;

    public WordAdapter(Context context, List<Word> wordList) {
        super(context, R.layout.item_word, wordList);
        this.context = context;
        this.wordList = wordList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_word, parent, false);
        }

        Word word = wordList.get(position);

        TextView txtWord = view.findViewById(R.id.txtWord);
        TextView txtPronunciation = view.findViewById(R.id.txtPronunciation);
        TextView txtMeaning = view.findViewById(R.id.txtMeaning);
        TextView txtExample = view.findViewById(R.id.txtExample);

        txtWord.setText(word.getWord());
        txtPronunciation.setText(word.getPronunciation());
        txtMeaning.setText("Nghĩa: " + word.getMeaning());
        txtExample.setText("Ví dụ: " + word.getExample());

        return view;
    }
}
