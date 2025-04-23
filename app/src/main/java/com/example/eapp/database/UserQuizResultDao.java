package com.example.eapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.eapp.models.UserQuizResult;

import java.util.List;

@Dao
public interface UserQuizResultDao {

    @Insert
    void insert(UserQuizResult userQuizResult);

    // Truy vấn lịch sử bài kiểm tra của người dùng theo topicId
    @Query("SELECT * FROM user_quiz_results WHERE topicId = :topicId")
    List<UserQuizResult> getQuizResultsByTopic(int topicId);


    // Lấy lịch sử bài kiểm tra theo userId và topicId
    @Query("SELECT * FROM user_quiz_results WHERE userId = :userId AND topicId = :topicId ORDER BY id DESC")  // Sắp xếp bài kiểm tra mới nhất lên trên cùng
    List<UserQuizResult> getQuizResultsByUserIdAndTopic(int userId, int topicId);
}
