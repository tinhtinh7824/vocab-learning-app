package com.example.eapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eapp.models.Word;

import java.util.List;

@Dao
public interface WordDao {

    @Insert
    void insertWord(Word word);

    @Update
    void updateWord(Word word);

    @Delete
    void deleteWord(Word word);

    // Thêm phương thức để xóa tất cả từ vựng
    @Query("DELETE FROM words")
    void deleteAllWords();  // Phương thức xóa toàn bộ từ vựng

    // --- Truy vấn theo topic ---
    @Query("SELECT * FROM words WHERE topicId = :topicId")
    List<Word> getWordsByTopic(int topicId);


    // --- Lấy toàn bộ từ vựng ---
    @Query("SELECT * FROM words")
    List<Word> getAllWords();

    @Query("SELECT * FROM words WHERE topicId = :topicId")
    List<Word> getAllWordsByTopic(int topicId);

    @Query("SELECT * FROM words WHERE word = :wordName LIMIT 1")
    Word getWordByName(String wordName);  // Kiểm tra nếu từ vựng đã có

}
