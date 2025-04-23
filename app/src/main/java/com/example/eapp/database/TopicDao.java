package com.example.eapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eapp.models.Topic;

import java.util.List;

@Dao
public interface TopicDao {

    // Phương thức để thêm một chủ đề từ vựng
    @Insert
    void insertTopic(Topic topic);

    // Phương thức để cập nhật thông tin của một chủ đề
    @Update
    void updateTopic(Topic topic);

    // Phương thức để xóa chủ đề từ vựng
    @Delete
    void deleteTopic(Topic topic);

    // Phương thức để lấy danh sách tất cả các chủ đề
    @Query("SELECT * FROM topics")
    List<Topic> getAllTopics();

    // Phương thức để tìm kiếm chủ đề theo tên (dùng LIKE cho phép tìm kiếm với từ khóa)
    @Query("SELECT * FROM topics WHERE name LIKE :query")
    List<Topic> searchTopics(String query);

    // Phương thức để lấy một chủ đề theo ID
    @Query("SELECT * FROM topics WHERE id = :topicId")
    Topic getTopicById(int topicId);  // Lấy chủ đề theo ID (dành cho Room)
}
