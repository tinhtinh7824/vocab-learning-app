package com.example.vocablearningapp.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vocablearningapp.R;
import com.example.vocablearningapp.database.VocabDAO;
import com.example.vocablearningapp.models.Word;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ListView listViewHistory;
    private List<String> recentWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listViewHistory = findViewById(R.id.listViewHistory);

        VocabDAO vocabDAO = new VocabDAO(this);
        List<Word> recentList = vocabDAO.getRecentStudiedWords();

        recentWords = new ArrayList<>();
        for (Word word : recentList) {
            recentWords.add(word.getWord() + " - " + word.getMeaning());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, recentWords);

        listViewHistory.setAdapter(adapter);
    }
}
