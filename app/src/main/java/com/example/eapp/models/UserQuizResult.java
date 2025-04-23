package com.example.eapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_quiz_results")
public class UserQuizResult {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;          // User ID - để lưu trữ người dùng thực hiện bài kiểm tra
    private String topicName;    // Tên chủ đề của bài kiểm tra
    private int score;           // Điểm số của người dùng
    private int topicId;         // Topic ID - liên kết với chủ đề
    private String date;         // Ngày kiểm tra

    public UserQuizResult(int userId, String topicName, int score, int topicId, String date) {
        this.userId = userId;
        this.topicName = topicName;
        this.score = score;
        this.topicId = topicId;  // Khởi tạo topicId
        this.date = date;        // Khởi tạo ngày
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
