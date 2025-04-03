package com.example.appta.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.appta.data.Dao.TopicDao;
import com.example.appta.data.Dao.WordDao;
import com.example.appta.data.model.Topic;
import com.example.appta.data.model.Word;

@Database(entities = {Topic.class, Word.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    // Abstract methods để Room có thể sử dụng cho DAO
    public abstract TopicDao topicDao();
    public abstract WordDao wordDao();

    // Phương thức để lấy instance của database
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "vocabulary_db")
                    .fallbackToDestructiveMigration() // Giúp Room xử lý khi có thay đổi trong schema
                    .allowMainThreadQueries() // Cho phép thực thi trên main thread (không khuyến khích trong sản phẩm thực)
                    .build();
        }
        return instance;
    }
}
