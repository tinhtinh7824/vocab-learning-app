package com.example.eapp.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eapp.R;
import com.example.eapp.database.AppDatabase;
import com.example.eapp.models.Word;

import java.util.List;

public class FlashcardActivity extends AppCompatActivity {

    private LinearLayout frontCard, backCard;
    private TextView txtWord, txtMeaning, txtPronunciation;
    private Button btnNext;

    private List<Word> vocabList;
    private int currentIndex = 0;
    private boolean isFront = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        // Hiển thị nút quay lại trên ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        frontCard = findViewById(R.id.frontCard);
        backCard = findViewById(R.id.backCard);
        txtWord = findViewById(R.id.txtWord);
        txtMeaning = findViewById(R.id.txtMeaning);
        txtPronunciation = findViewById(R.id.txtPronunciation);
        btnNext = findViewById(R.id.btnNext);

        // Load từ vựng từ database bằng AsyncTask
        new LoadWordsTask().execute();

        // Lật thẻ
        findViewById(R.id.flashcardContainer).setOnClickListener(v -> flipCard());

        // Nút tiếp
        btnNext.setOnClickListener(v -> {
            currentIndex = (currentIndex + 1) % vocabList.size();
            isFront = true;
            showFlashcard(currentIndex);
        });
    }

    private class LoadWordsTask extends AsyncTask<Void, Void, List<Word>> {
        @Override
        protected List<Word> doInBackground(Void... voids) {
            // Lấy topicId từ Intent
            int topicId = getIntent().getIntExtra("topicId", -1);

            // Truy vấn 10 từ vựng của chủ đề
            return AppDatabase.getInstance(getApplicationContext())
                    .wordDao()
                    .getWordsByTopic(topicId);  // Chỉ lấy 10 từ của chủ đề
        }

        @Override
        protected void onPostExecute(List<Word> words) {
            vocabList = words;
            if (vocabList != null && !vocabList.isEmpty()) {
                showFlashcard(currentIndex);
            } else {
                Toast.makeText(FlashcardActivity.this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void showFlashcard(int index) {
        Word word = vocabList.get(index);

        // Mặt trước: từ tiếng Anh và phiên âm
        txtWord.setText(word.getWord());
        txtPronunciation.setText("Phiên âm: " + word.getPronunciation());

        // Mặt sau: nghĩa tiếng Việt
        txtMeaning.setText("Nghĩa: " + word.getMeaning());

        // Hiển thị mặt trước và ẩn mặt sau khi bắt đầu
        if (isFront) {
            frontCard.setVisibility(View.VISIBLE);
            frontCard.setRotationY(0f);
            backCard.setVisibility(View.GONE);
            backCard.setRotationY(0f);
        } else {
            // Mặt sau khi lật
            frontCard.setVisibility(View.GONE);
            backCard.setVisibility(View.VISIBLE);
        }
    }



    private void flipCard() {
        if (isFront) {
            frontCard.animate()
                    .rotationY(90f) // Lật mặt trước
                    .setDuration(200)
                    .withEndAction(() -> {
                        frontCard.setVisibility(View.GONE);  // Ẩn mặt trước
                        backCard.setRotationY(-90f);  // Đặt mặt sau vào vị trí lật
                        backCard.setVisibility(View.VISIBLE);  // Hiển thị mặt sau
                        backCard.animate().rotationY(0f).setDuration(200).start();  // Lật mặt sau về vị trí ban đầu
                    }).start();
        } else {
            backCard.animate()
                    .rotationY(90f)  // Lật mặt sau
                    .setDuration(200)
                    .withEndAction(() -> {
                        backCard.setVisibility(View.GONE);  // Ẩn mặt sau
                        frontCard.setRotationY(-90f);  // Đặt mặt trước vào vị trí lật
                        frontCard.setVisibility(View.VISIBLE);  // Hiển thị mặt trước
                        frontCard.animate().rotationY(0f).setDuration(200).start();  // Lật mặt trước về vị trí ban đầu
                    }).start();
        }
        isFront = !isFront;  // Đổi trạng thái mặt trước/mặt sau
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
