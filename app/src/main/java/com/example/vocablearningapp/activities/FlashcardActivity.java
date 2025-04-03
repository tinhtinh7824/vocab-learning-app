package com.example.vocablearningapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vocablearningapp.R;
import com.example.vocablearningapp.database.VocabDAO;
import com.example.vocablearningapp.models.Word;

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

        frontCard = findViewById(R.id.frontCard);
        backCard = findViewById(R.id.backCard);
        txtWord = findViewById(R.id.txtWord);
        txtMeaning = findViewById(R.id.txtMeaning);
        txtPronunciation = findViewById(R.id.txtPronunciation);
        btnNext = findViewById(R.id.btnNext);

        // Load dữ liệu từ SQLite
        VocabDAO vocabDAO = new VocabDAO(this);
        vocabList = vocabDAO.getAllWords();

        if (!vocabList.isEmpty()) {
            showFlashcard(currentIndex);
        }

        // Lật thẻ khi click vào vùng chứa
        View flashcardContainer = findViewById(R.id.flashcardContainer);
        flashcardContainer.setOnClickListener(v -> flipCard());

        // Nút tiếp theo
        btnNext.setOnClickListener(v -> {
            currentIndex = (currentIndex + 1) % vocabList.size();
            isFront = true; // reset lại trạng thái mặt trước
            showFlashcard(currentIndex);
        });
    }

    private void showFlashcard(int index) {
        Word word = vocabList.get(index);
        txtWord.setText(word.getWord());
        txtMeaning.setText("Nghĩa: " + word.getMeaning());
        txtPronunciation.setText("Phiên âm: " + word.getPronunciation());

        // Luôn hiển thị mặt trước khi chuyển sang từ mới
        frontCard.setVisibility(View.VISIBLE);
        frontCard.setRotationY(0f);
        backCard.setVisibility(View.GONE);
        backCard.setRotationY(0f);
    }

    private void flipCard() {
        if (isFront) {
            // Lật từ trước → sau
            frontCard.animate()
                    .rotationY(90f)
                    .setDuration(200)
                    .withEndAction(() -> {
                        frontCard.setVisibility(View.GONE);
                        backCard.setRotationY(-90f);
                        backCard.setVisibility(View.VISIBLE);
                        backCard.animate().rotationY(0f).setDuration(200).start();
                    })
                    .start();
        } else {
            // Lật từ sau → trước
            backCard.animate()
                    .rotationY(90f)
                    .setDuration(200)
                    .withEndAction(() -> {
                        backCard.setVisibility(View.GONE);
                        frontCard.setRotationY(-90f);
                        frontCard.setVisibility(View.VISIBLE);
                        frontCard.animate().rotationY(0f).setDuration(200).start();
                    })
                    .start();
        }
        isFront = !isFront;
    }
}
