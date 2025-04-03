package com.example.appta.data.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.appta.data.model.Word;
import java.util.List;

@Dao
public interface WordDao {
    @Insert
    void insertWord(Word word);

    @Query("SELECT * FROM words WHERE topicId = :topicId")
    List<Word> getWordsByTopic(int topicId);
}
