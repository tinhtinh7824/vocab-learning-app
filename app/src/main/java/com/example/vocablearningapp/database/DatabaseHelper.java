package com.example.vocablearningapp.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "vocab_learning_app_data.sql";
    private static final int DATABASE_VERSION = 1;

    // Câu lệnh tạo bảng
    private static final String CREATE_ACCOUNT_TABLE = "CREATE TABLE Account (" +
            "userID TEXT PRIMARY KEY," +
            "userName TEXT NOT NULL," +
            "email TEXT UNIQUE NOT NULL," +
            "passWord TEXT NOT NULL);";

    private static final String CREATE_TOPIC_TABLE = "CREATE TABLE Topic (" +
            "topicID TEXT PRIMARY KEY," +
            "topicName TEXT NOT NULL);";

    private static final String CREATE_WORD_TABLE = "CREATE TABLE Word (" +
            "wordID TEXT PRIMARY KEY," +
            "word TEXT NOT NULL," +
            "wordType TEXT," +
            "meaning TEXT," +
            "pronunciation TEXT," +
            "example TEXT," +
            "topicID TEXT," +
            "FOREIGN KEY (topicID) REFERENCES Topic(topicID) ON DELETE SET NULL);";

    private static final String CREATE_QUESTION_TABLE = "CREATE TABLE Question (" +
            "questionID TEXT PRIMARY KEY," +
            "questionText TEXT NOT NULL," +
            "wordID TEXT UNIQUE," +
            "FOREIGN KEY (wordID) REFERENCES Word(wordID) ON DELETE CASCADE);";

    private static final String CREATE_QUIZ_TABLE = "CREATE TABLE Quiz (" +
            "quizID TEXT PRIMARY KEY," +
            "quizName TEXT NOT NULL," +
            "duration TEXT);";

    private static final String CREATE_QUIZ_QUESTION_TABLE = "CREATE TABLE Quiz_Question (" +
            "quizID TEXT," +
            "questionID TEXT," +
            "PRIMARY KEY (quizID, questionID)," +
            "FOREIGN KEY (quizID) REFERENCES Quiz(quizID) ON DELETE CASCADE," +
            "FOREIGN KEY (questionID) REFERENCES Question(questionID) ON DELETE CASCADE);";

    private static final String CREATE_NOTIFICATION_TABLE = "CREATE TABLE Notification (" +
            "notificationID TEXT PRIMARY KEY," +
            "userID TEXT," +
            "message TEXT NOT NULL," +
            "createdAt DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY (userID) REFERENCES Account(userID) ON DELETE CASCADE);";

    private static final String CREATE_REVIEW_HISTORY_TABLE = "CREATE TABLE ReviewHistory (" +
            "reviewID TEXT PRIMARY KEY," +
            "userID TEXT," +
            "wordID TEXT," +
            "reviewedAt DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "score INTEGER CHECK (score BETWEEN 0 AND 100)," +
            "FOREIGN KEY (userID) REFERENCES Account(userID) ON DELETE CASCADE," +
            "FOREIGN KEY (wordID) REFERENCES Word(wordID) ON DELETE CASCADE);";

    private static final String CREATE_USER_QUIZ_TABLE = "CREATE TABLE User_Quiz (" +
            "userID TEXT," +
            "quizID TEXT," +
            "PRIMARY KEY (userID, quizID)," +
            "FOREIGN KEY (userID) REFERENCES Account(userID) ON DELETE CASCADE," +
            "FOREIGN KEY (quizID) REFERENCES Quiz(quizID) ON DELETE CASCADE);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_TOPIC_TABLE);
        db.execSQL(CREATE_WORD_TABLE);
        db.execSQL(CREATE_QUESTION_TABLE);
        db.execSQL(CREATE_QUIZ_TABLE);
        db.execSQL(CREATE_QUIZ_QUESTION_TABLE);
        db.execSQL(CREATE_NOTIFICATION_TABLE);
        db.execSQL(CREATE_REVIEW_HISTORY_TABLE);
        db.execSQL(CREATE_USER_QUIZ_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Account");
        db.execSQL("DROP TABLE IF EXISTS Topic");
        db.execSQL("DROP TABLE IF EXISTS Word");
        db.execSQL("DROP TABLE IF EXISTS Question");
        db.execSQL("DROP TABLE IF EXISTS Quiz");
        db.execSQL("DROP TABLE IF EXISTS Quiz_Question");
        db.execSQL("DROP TABLE IF EXISTS Notification");
        db.execSQL("DROP TABLE IF EXISTS ReviewHistory");
        db.execSQL("DROP TABLE IF EXISTS User_Quiz");
        onCreate(db);
    }
}
