package com.example.vocablearningapp;

import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO Account (userID, userName, email, passWord) VALUES ('1', 'John Doe', 'john@example.com', 'password123');");
        db.execSQL("INSERT INTO Topic (topicID, topicName) VALUES ('1', 'Vocabulary');");
        db.execSQL("INSERT INTO Word (wordID, word, meaning, topicID) VALUES ('1', 'Hello', 'Xin chào', '1');");

        dbHelper.close();
    }
}