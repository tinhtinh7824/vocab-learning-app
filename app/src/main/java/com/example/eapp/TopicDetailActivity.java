package com.example.eapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eapp.R;
import com.example.eapp.activities.QuizActivity;
import com.example.eapp.activities.VocabularyListActivity;
import com.example.eapp.activities.FlashcardActivity;
import com.example.eapp.activities.QuizHistoryActivity;  // Import QuizHistoryActivity

public class TopicDetailActivity extends AppCompatActivity {

    private TextView topicNameTextView;
    private Button btnVocabularyList, btnFlashcard, btnReviewTopic, btnQuizHistory;  // Thêm nút lịch sử bài kiểm tra

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        // Hiển thị nút quay lại trên ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Lấy topicId từ Intent
        int topicId = getIntent().getIntExtra("topicId", -1);

        // Hiển thị tên chủ đề tương ứng với topicId
        topicNameTextView = findViewById(R.id.topicName);
        topicNameTextView.setText(getTopicNameById(topicId));

        // Khởi tạo các nút
        btnVocabularyList = findViewById(R.id.btnVocabularyList);
        btnFlashcard = findViewById(R.id.btnFlashcard);
        btnReviewTopic = findViewById(R.id.btnReviewTopic);
        btnQuizHistory = findViewById(R.id.btnQuizHistory);  // Nút lịch sử bài kiểm tra

        // Xử lý sự kiện khi nhấn vào "Danh sách từ vựng"
        btnVocabularyList.setOnClickListener(v -> {
            Intent intent = new Intent(TopicDetailActivity.this, VocabularyListActivity.class);
            intent.putExtra("topicId", topicId); // Truyền topicId
            startActivity(intent);
        });

        // Xử lý sự kiện khi nhấn vào "Flashcard"
        btnFlashcard.setOnClickListener(v -> {
            Intent intent = new Intent(TopicDetailActivity.this, FlashcardActivity.class);
            intent.putExtra("topicId", topicId); // Truyền topicId
            startActivity(intent);
        });

        // Xử lý sự kiện khi nhấn vào "Ôn tập từ vựng"
        btnReviewTopic.setOnClickListener(v -> {
            Intent intent = new Intent(TopicDetailActivity.this, QuizActivity.class);
            intent.putExtra("topicId", topicId); // Truyền topicId
            startActivity(intent);
        });

        // Xử lý sự kiện khi nhấn vào "Lịch sử bài kiểm tra"
        btnQuizHistory.setOnClickListener(v -> {
            Intent intent = new Intent(TopicDetailActivity.this, QuizHistoryActivity.class);
            intent.putExtra("topicId", topicId); // Truyền topicId
            startActivity(intent);
        });
    }

    // Hàm trả về tên chủ đề theo topicId
    private String getTopicNameById(int topicId) {
        switch (topicId) {
            case 1:
                return "Trái cây";
            case 2:
                return "Động vật";
            case 3:
                return "Màu sắc";
            case 4:
                return "Số đếm";
            case 5:
                return "Nghề nghiệp";
            case 6:
                return "Giao thông";
            default:
                return "Chủ đề không xác định";
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
