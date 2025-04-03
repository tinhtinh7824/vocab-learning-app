package com.example.vocablearningapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vocablearningapp.activities.FlashcardActivity;
import com.example.vocablearningapp.activities.QuizActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, FlashcardActivity.class);
        startActivity(intent);



        // Nếu không muốn quay lại màn hình này thì gọi finish()
        finish();
    }
}
