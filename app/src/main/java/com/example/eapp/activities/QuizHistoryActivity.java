package com.example.eapp.activities;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eapp.R;
import com.example.eapp.adapters.QuizHistoryAdapter;
import com.example.eapp.database.AppDatabase;
import com.example.eapp.models.UserQuizResult;

import java.util.List;

public class QuizHistoryActivity extends AppCompatActivity {

    private ListView quizHistoryListView;
    private QuizHistoryAdapter quizHistoryAdapter;
    private AppDatabase db;
    private int userId;
    private int topicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_history);

        // Hiển thị nút quay lại trên ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        quizHistoryListView = findViewById(R.id.quizHistoryListView);
        db = AppDatabase.getInstance(this);

        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);  // Lấy userId đã lưu trước đó

        if (userId == -1) {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Nhận topicId từ Intent
        topicId = getIntent().getIntExtra("topicId", -1);
        if (topicId == -1) {
            Toast.makeText(this, "Chủ đề không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Load dữ liệu lịch sử bài kiểm tra
        new LoadQuizHistoryTask().execute(userId, topicId);
    }

    private class LoadQuizHistoryTask extends AsyncTask<Integer, Void, List<UserQuizResult>> {
        @Override
        protected List<UserQuizResult> doInBackground(Integer... params) {
            // Lấy lịch sử bài kiểm tra theo userId và topicId
            return db.userQuizResultDao().getQuizResultsByUserIdAndTopic(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(List<UserQuizResult> quizResults) {
            if (quizResults != null && !quizResults.isEmpty()) {
                quizHistoryAdapter = new QuizHistoryAdapter(QuizHistoryActivity.this, quizResults);
                quizHistoryListView.setAdapter(quizHistoryAdapter);
            } else {
                Toast.makeText(QuizHistoryActivity.this, "Không có lịch sử bài kiểm tra cho chủ đề này", Toast.LENGTH_SHORT).show();
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
