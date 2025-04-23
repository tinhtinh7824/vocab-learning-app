package com.example.eapp.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eapp.R;
import com.example.eapp.database.AppDatabase;
import com.example.eapp.models.UserQuizResult;
import com.example.eapp.models.Word;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView, resultTextView, timerTextView, questionCounterTextView;
    private LinearLayout choicesGroup;
    private Button nextButton;
    private MediaPlayer mediaPlayer;
    private CountDownTimer countDownTimer;

    private List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private static final long TIME_PER_QUESTION = 30000; // 30 giây
    private boolean isAnswerChecked = false;

    private static class QuizQuestion {
        String questionText;
        List<String> choices;
        String correctAnswer;

        QuizQuestion(String questionText, List<String> choices, String correctAnswer) {
            this.questionText = questionText;
            this.choices = choices;
            this.correctAnswer = correctAnswer;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Hiển thị nút quay lại trên ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        questionTextView = findViewById(R.id.questionTextView);
        resultTextView = findViewById(R.id.resultTextView);
        timerTextView = findViewById(R.id.timerTextView);
        questionCounterTextView = findViewById(R.id.questionCounterTextView);
        nextButton = findViewById(R.id.nextButton);
        choicesGroup = findViewById(R.id.choicesGroup);

        int topicId = getIntent().getIntExtra("topicId", -1);  // Nhận topicId từ Intent

        if (topicId == -1) {
            Toast.makeText(this, "Chủ đề không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Sử dụng AsyncTask để thực hiện truy vấn cơ sở dữ liệu
        new LoadWordsTask().execute(topicId);

        nextButton.setOnClickListener(v -> {
            if (!isAnswerChecked) {
                if (!isChoiceSelected()) {
                    Toast.makeText(this, "Vui lòng chọn một đáp án!", Toast.LENGTH_SHORT).show();
                    return;
                }
                checkAnswer();
                isAnswerChecked = true;
                nextButton.setText("Tiếp tục");
            } else {
                currentQuestionIndex++;
                if (currentQuestionIndex < quizQuestions.size()) {
                    showCurrentQuestion();
                } else {
                    showFinalResult();
                }
            }
        });
    }

    private class LoadWordsTask extends AsyncTask<Integer, Void, List<Word>> {
        @Override
        protected List<Word> doInBackground(Integer... topicIds) {
            AppDatabase db = AppDatabase.getInstance(QuizActivity.this);
            return db.wordDao().getWordsByTopic(topicIds[0]);  // Lấy 10 từ vựng của chủ đề
        }

        @Override
        protected void onPostExecute(List<Word> words) {
            if (words != null && !words.isEmpty()) {
                Collections.shuffle(words);
                quizQuestions = generateQuizQuestions(words, 10);  // Chỉ lấy 10 câu hỏi từ 10 từ vựng
                showCurrentQuestion();
            } else {
                Toast.makeText(QuizActivity.this, "Không có dữ liệu từ vựng cho chủ đề này", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showCurrentQuestion() {
        resultTextView.setText("");
        choicesGroup.removeAllViews();

        QuizQuestion question = quizQuestions.get(currentQuestionIndex);
        questionTextView.setText((currentQuestionIndex + 1) + ". " + question.questionText);
        questionCounterTextView.setText((currentQuestionIndex + 1) + "/" + quizQuestions.size());

        for (String choice : question.choices) {
            TextView option = new TextView(this);
            option.setText(choice);
            option.setTextSize(16f);
            option.setTextColor(Color.BLACK);
            option.setBackgroundResource(R.drawable.bg_answer_normal);
            option.setPadding(32, 24, 32, 24);
            option.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 16, 0, 0);
            option.setLayoutParams(params);

            option.setOnClickListener(view -> {
                clearSelection();
                view.setBackgroundResource(R.drawable.bg_answer_selected);
                view.setTag("selected");
                nextButton.setEnabled(true);
            });

            choicesGroup.addView(option);
        }

        nextButton.setEnabled(false);
        isAnswerChecked = false;
        nextButton.setText("Đồng ý");
        startTimer();
    }

    private void clearSelection() {
        for (int i = 0; i < choicesGroup.getChildCount(); i++) {
            View child = choicesGroup.getChildAt(i);
            child.setBackgroundResource(R.drawable.bg_answer_normal);
            child.setTag(null);
        }
    }

    private boolean isChoiceSelected() {
        for (int i = 0; i < choicesGroup.getChildCount(); i++) {
            if ("selected".equals(choicesGroup.getChildAt(i).getTag())) return true;
        }
        return false;
    }

    private void checkAnswer() {
        String correct = quizQuestions.get(currentQuestionIndex).correctAnswer;

        for (int i = 0; i < choicesGroup.getChildCount(); i++) {
            TextView optionView = (TextView) choicesGroup.getChildAt(i);
            String optionText = optionView.getText().toString();
            boolean isSelected = "selected".equals(optionView.getTag());

            if (optionText.equals(correct)) {
                optionView.setBackgroundResource(R.drawable.bg_answer_correct);
                optionView.setTextColor(Color.WHITE);
            } else if (isSelected) {
                optionView.setBackgroundResource(R.drawable.bg_answer_wrong);
                optionView.setTextColor(Color.WHITE);
            } else {
                optionView.setAlpha(0.4f);
            }

            optionView.setEnabled(false);
        }

        String selected = getSelectedText();
        boolean isCorrect = selected.equals(correct);

        resultTextView.setText(isCorrect
                ? "✅ Chính xác!"
                : "❌ Sai rồi! Đáp án đúng: " + correct);

        if (isCorrect) {
            score++;
            playSound(R.raw.correct);
        } else {
            playSound(R.raw.incorrect);
        }

        nextButton.setEnabled(true);
    }

    private String getSelectedText() {
        for (int i = 0; i < choicesGroup.getChildCount(); i++) {
            View child = choicesGroup.getChildAt(i);
            if ("selected".equals(child.getTag())) {
                return ((TextView) child).getText().toString();
            }
        }
        return "";
    }

    private void showFinalResult() {
        if (countDownTimer != null) countDownTimer.cancel();

        questionTextView.setText("🎉 Bạn đã hoàn thành Quiz!");
        resultTextView.setText("Điểm của bạn: " + score + "/" + quizQuestions.size());
        choicesGroup.setVisibility(View.GONE);
        nextButton.setText("Hoàn tất");

        // Lưu kết quả vào cơ sở dữ liệu
        saveQuizResult();

        // Khi nhấn "Hoàn tất", quay lại màn hình trước đó
        nextButton.setOnClickListener(v -> {
            onBackPressed(); // Quay lại màn hình trước đó
        });

        playSound(R.raw.chucmung);
    }



    private void saveQuizResult() {
        // Lấy thông tin người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1); // Lấy userId đã lưu trước đó

        // Lấy thông tin chủ đề và điểm số
        int topicId = getIntent().getIntExtra("topicId", -1);
        String topicName = getTopicNameById(topicId);
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        // Tạo đối tượng UserQuizResult
        UserQuizResult quizResult = new UserQuizResult(userId, topicName, score, topicId, currentDate);

        // Lưu kết quả vào cơ sở dữ liệu trong background thread
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(QuizActivity.this);
            db.userQuizResultDao().insert(quizResult); // Lưu kết quả vào database
        }).start();
    }




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

    private List<QuizQuestion> generateQuizQuestions(List<Word> words, int count) {
        List<QuizQuestion> questions = new ArrayList<>();
        Random rand = new Random();

        // Lấy 10 câu hỏi cho mỗi chủ đề, mỗi câu hỏi từ một từ duy nhất
        Set<Integer> usedIndexes = new HashSet<>();

        while (usedIndexes.size() < count) {
            int randomIndex = rand.nextInt(words.size());

            if (!usedIndexes.contains(randomIndex)) {
                usedIndexes.add(randomIndex);

                Word correctWord = words.get(randomIndex);
                String correctMeaning = correctWord.getMeaning();

                Set<String> options = new HashSet<>();
                options.add(correctMeaning); // Đảm bảo đáp án đúng có trong các lựa chọn

                // Lấy các đáp án sai ngẫu nhiên từ các từ khác trong chủ đề
                while (options.size() < 4) {
                    String randomMeaning = words.get(rand.nextInt(words.size())).getMeaning();
                    options.add(randomMeaning); // Thêm đáp án sai
                }

                // Random đáp án và tạo câu hỏi
                List<String> shuffledChoices = new ArrayList<>(options);
                Collections.shuffle(shuffledChoices); // Trộn các lựa chọn đáp án

                questions.add(new QuizQuestion(correctWord.getWord(), shuffledChoices, correctMeaning));
            }
        }

        return questions;
    }

    private void startTimer() {
        if (countDownTimer != null) countDownTimer.cancel();

        countDownTimer = new CountDownTimer(TIME_PER_QUESTION, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText((millisUntilFinished / 1000) + " giây");
            }

            public void onFinish() {
                if (!isAnswerChecked) {
                    checkAnswer();
                    isAnswerChecked = true;
                    nextButton.setText("Tiếp tục");
                    nextButton.setEnabled(true);
                }
            }
        };
        countDownTimer.start();
    }

    private void playSound(int resId) {
        if (mediaPlayer != null) mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(this, resId);
        mediaPlayer.start();
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
