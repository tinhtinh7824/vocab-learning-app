package com.example.appta.data.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appta.data.model.Topic;
import java.util.List;

@Dao
public interface TopicDao {
    @Insert
    void insertTopics(Topic topic);
    @Update
    void updateTopic(Topic topic);

    @Delete
    void deleteTopic(Topic topic);

    @Query("SELECT * FROM topics")
    List<Topic> getAllTopics();

    // Thêm phương thức tìm kiếm chủ đề
    @Query("SELECT * FROM topics WHERE name LIKE :query")
    List<Topic> searchTopics(String query);
}
