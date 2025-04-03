package com.example.appta.ui;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appta.R;
import com.example.appta.adapter.WordAdapter;
import com.example.appta.data.AppDatabase;
import com.example.appta.data.model.Word;

import java.util.List;

public class WordActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WordAdapter wordAdapter;
    private List<Word> wordList;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        // Nhận topicId từ Intent
        int topicId = getIntent().getIntExtra("topicId", -1);

        // Kiểm tra topicId hợp lệ
        if (topicId != -1) {
            // Lấy instance của database
            database = AppDatabase.getInstance(this);

            // Lấy danh sách từ vựng theo topicId
            wordList = database.wordDao().getWordsByTopic(topicId);

            // Kiểm tra nếu danh sách từ vựng không rỗng
            if (wordList != null && !wordList.isEmpty()) {
                // Thiết lập RecyclerView
                recyclerView = findViewById(R.id.recyclerViewWord);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                // Tạo adapter và gắn vào RecyclerView
                wordAdapter = new WordAdapter(this, wordList);
                recyclerView.setAdapter(wordAdapter);
            } else {
                // Nếu không có từ vựng nào cho topic này
                Toast.makeText(this, "Không có từ vựng cho chủ đề này", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Xử lý trường hợp topicId không hợp lệ
            Toast.makeText(this, "Topic ID không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }
}
