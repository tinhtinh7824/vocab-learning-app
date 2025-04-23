package com.example.eapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.eapp.models.Topic;
import com.example.eapp.models.User;
import com.example.eapp.models.Word;
import com.example.eapp.models.UserQuizResult;

@Database(entities = {Word.class, Topic.class, User.class, UserQuizResult.class}, version = 15)  // Tăng version lên 10
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract WordDao wordDao();
    public abstract TopicDao topicDao();
    public abstract UserDao userDao();
    public abstract UserQuizResultDao userQuizResultDao();  // Thêm dao cho UserQuizResult

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "vocab_learning_app_room.db")
                    .fallbackToDestructiveMigration() // Cho phép xóa cơ sở dữ liệu cũ nếu migration không thành công
                    .build();
        }
        return instance;
    }

}
