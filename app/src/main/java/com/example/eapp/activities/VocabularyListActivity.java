package com.example.eapp.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eapp.R;
import com.example.eapp.adapters.WordAdapter;
import com.example.eapp.database.AppDatabase;
import com.example.eapp.models.Word;

import java.util.List;

public class VocabularyListActivity extends AppCompatActivity {

    private ListView listViewWords;
    private WordAdapter wordAdapter;
    private AppDatabase db;
    private int topicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_list);

        // Hiển thị nút quay lại trên ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        topicId = getIntent().getIntExtra("topicId", -1);
        if (topicId == -1) {
            Toast.makeText(this, "Chủ đề không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        db = AppDatabase.getInstance(this); // Khởi tạo cơ sở dữ liệu
        listViewWords = findViewById(R.id.listViewWords);

        // Lấy danh sách từ vựng của chủ đề từ cơ sở dữ liệu
        new LoadWordsTask().execute();
    }

    private class LoadWordsTask extends AsyncTask<Void, Void, List<Word>> {
        @Override
        protected List<Word> doInBackground(Void... voids) {
            return db.wordDao().getWordsByTopic(topicId); // Lấy 10 từ vựng của chủ đề
        }

        @Override
        protected void onPostExecute(List<Word> wordList) {
            super.onPostExecute(wordList);
            if (wordList != null && !wordList.isEmpty()) {
                wordAdapter = new WordAdapter(VocabularyListActivity.this, wordList);
                listViewWords.setAdapter(wordAdapter);
            } else {
                Toast.makeText(VocabularyListActivity.this, "Không có từ vựng cho chủ đề này", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Xử lý sự kiện khi nhấn nút quay lại
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Quay lại màn hình trước đó
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
