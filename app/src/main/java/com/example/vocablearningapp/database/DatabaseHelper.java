package com.example.vocablearningapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "VocabApp.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Word (" +
                "wordID TEXT PRIMARY KEY, " +
                "word TEXT, " +
                "wordType TEXT, " +
                "meaning TEXT, " +
                "pronunciation TEXT, " +
                "example TEXT, " +
                "topicID TEXT, " +
                "isFavorite INTEGER DEFAULT 0, " +          // 🟡 từ yêu thích: 0 = không, 1 = có
                "lastStudied INTEGER DEFAULT 0)");          // 🔵 thời gian học gần nhất
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý cập nhật DB nếu cần
        db.execSQL("DROP TABLE IF EXISTS Word");
        onCreate(db);
    }



}
