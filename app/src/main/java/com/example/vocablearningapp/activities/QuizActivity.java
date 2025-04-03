package com.example.vocablearningapp.activities;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vocablearningapp.R;
import com.example.vocablearningapp.database.VocabDAO;
import com.example.vocablearningapp.models.Word;

import java.util.*;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView, resultTextView, timerTextView;
    private RadioGroup choicesGroup;
    private boolean isAnswerChecked = false;

    private CountDownTimer countDownTimer;

    private static final long TIME_PER_QUESTION = 30_000; // 30 giây

    private Button nextButton;

    private MediaPlayer mediaPlayer;


    private List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    class QuizQuestion {
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

        questionTextView = findViewById(R.id.questionTextView);
        TextView questionCounterTextView = findViewById(R.id.questionCounterTextView);

        choicesGroup = findViewById(R.id.choicesGroup);

        timerTextView = findViewById(R.id.timerTextView);


        resultTextView = findViewById(R.id.resultTextView);
        nextButton = findViewById(R.id.nextButton);

        // Lấy dữ liệu từ database
        VocabDAO vocabDAO = new VocabDAO(this);
        vocabDAO.insertSampleWordsIfEmpty();
        List<Word> allWords = vocabDAO.getAllWords();
        Collections.shuffle(allWords);

        // Sinh 15 câu hỏi ngẫu nhiên
        quizQuestions = generateQuizQuestions(allWords, 15);
        showCurrentQuestion();

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

    private void clearSelection() {
        for (int i = 0; i < choicesGroup.getChildCount(); i++) {
            View child = choicesGroup.getChildAt(i);
            child.setBackgroundResource(R.drawable.bg_answer_normal);
            child.setTag(null);
        }
    }


    private boolean isChoiceSelected() {
        for (int i = 0; i < choicesGroup.getChildCount(); i++) {
            View child = choicesGroup.getChildAt(i);
            if ("selected".equals(child.getTag())) {
                return true;
            }
        }
        return false;
    }

    private void showCurrentQuestion() {
        resultTextView.setText("");
        choicesGroup.clearCheck();
        choicesGroup.removeAllViews();

        QuizQuestion question = quizQuestions.get(currentQuestionIndex);
        questionTextView.setText((currentQuestionIndex + 1) + ". " + question.questionText);

        TextView questionCounterTextView = findViewById(R.id.questionCounterTextView);
        questionCounterTextView.setText((currentQuestionIndex + 1) + "/" + quizQuestions.size());

        for (String choice : question.choices) {
            TextView option = new TextView(this);
            option.setText(choice);
            option.setTextSize(16f);
            option.setTextColor(Color.BLACK);
            option.setBackgroundResource(R.drawable.bg_answer_normal);
            option.setPadding(32, 24, 32, 24);

// ⬇ Sửa tại đây: Căn giữa văn bản trong TextView
            option.setGravity(Gravity.CENTER);

// layout params
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 16, 0, 0);
            option.setLayoutParams(params);

            option.setClickable(true);
            option.setFocusable(true);


            option.setOnClickListener(view -> {
                // Reset tất cả
                for (int i = 0; i < choicesGroup.getChildCount(); i++) {
                    View child = choicesGroup.getChildAt(i);
                    child.setBackgroundResource(R.drawable.bg_answer_normal);
                    child.setTag(null);
                }

                // Chọn cái mới
                view.setBackgroundResource(R.drawable.bg_answer_selected);
                view.setTag("selected");

                nextButton.setEnabled(true);
            });

            choicesGroup.addView(option);
        }



        choicesGroup.setOnCheckedChangeListener((group, checkedId) -> {
            nextButton.setEnabled(checkedId != -1);
        });

        nextButton.setEnabled(false);
        isAnswerChecked = false;
        nextButton.setText("Đồng ý");

        startTimer();


    }

    private void startTimer() {
        if (countDownTimer != null) countDownTimer.cancel();

        countDownTimer = new CountDownTimer(TIME_PER_QUESTION, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText((millisUntilFinished / 1000) + " giây");
            }

            public void onFinish() {
                if (!isAnswerChecked) {
                    // Hết giờ nhưng chưa chọn -> tự xử lý như chọn sai
                    checkAnswer();
                    isAnswerChecked = true;
                    nextButton.setText("Tiếp tục");
                    nextButton.setEnabled(true);
                }
            }
        };
        countDownTimer.start();
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
                optionView.setAlpha(0.4f); // làm mờ
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
            playSound(R.raw.correct);  // âm thanh đúng
        } else {
            playSound(R.raw.incorrect);    // âm thanh sai
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
        nextButton.setText("Tiếp tục");
        nextButton.setEnabled(true);

        // Phát âm thanh chúc mừng
        playSound(R.raw.chucmung);
    }


    private List<QuizQuestion> generateQuizQuestions(List<Word> words, int count) {
        List<QuizQuestion> questions = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < Math.min(count, words.size()); i++) {
            Word correctWord = words.get(i);
            String correctMeaning = correctWord.getMeaning();

            Set<String> options = new HashSet<>();
            options.add(correctMeaning);

            while (options.size() < 4) {
                String randomMeaning = words.get(rand.nextInt(words.size())).getMeaning();
                options.add(randomMeaning);
            }

            List<String> shuffledChoices = new ArrayList<>(options);
            Collections.shuffle(shuffledChoices);

            questions.add(new QuizQuestion(correctWord.getWord(), shuffledChoices, correctMeaning));
        }

        return questions;
    }

    private void playSound(int resId) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, resId);
        mediaPlayer.start();
    }

}
