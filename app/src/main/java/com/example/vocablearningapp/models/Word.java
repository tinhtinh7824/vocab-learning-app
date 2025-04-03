package com.example.appta.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "words")
public class Word {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String word;
    private String meaning;
    private int topicId; // Chủ đề của từ vựng

    // Constructor để khởi tạo đối tượng Word
    public Word(String word, String meaning, int topicId) {
        this.word = word;
        this.meaning = meaning;
        this.topicId = topicId;
    }

    // Getter và setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và setter cho word
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    // Getter và setter cho meaning
    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    // Getter và setter cho topicId
    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }
}
