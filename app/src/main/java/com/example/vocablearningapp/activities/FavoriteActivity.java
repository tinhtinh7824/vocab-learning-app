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

public class FavoriteActivity extends AppCompatActivity {

    private ListView listViewFavorites;
    private List<String> favoriteWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        listViewFavorites = findViewById(R.id.listViewFavorites);

        VocabDAO vocabDAO = new VocabDAO(this);
        List<Word> favorites = vocabDAO.getFavoriteWords();

        favoriteWords = new ArrayList<>();
        for (Word word : favorites) {
            favoriteWords.add(word.getWord() + " - " + word.getMeaning());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, favoriteWords);

        listViewFavorites.setAdapter(adapter);
    }
}
