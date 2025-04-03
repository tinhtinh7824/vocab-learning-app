package com.example.appta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appta.R;
import com.example.appta.data.model.Word;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {

    private Context context;
    private List<Word> wordList;

    public WordAdapter(Context context, List<Word> wordList) {
        this.context = context;
        this.wordList = wordList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_word, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Word word = wordList.get(position);
        holder.textWord.setText(word.getWord());
        holder.textMeaning.setText(word.getMeaning());
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textWord, textMeaning;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textWord = itemView.findViewById(R.id.textWord);
            textMeaning = itemView.findViewById(R.id.textMeaning);
        }
    }
}
