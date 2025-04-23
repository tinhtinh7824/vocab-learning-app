package com.example.eapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "words")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String word;
    private String meaning;
    private int topicId;
    private String pronunciation;  // Thêm trường phiên âm
    private String example;  // Thêm trường ví dụ

    // Constructor
    public Word(String word, String meaning, int topicId, String pronunciation, String example) {
        this.word = word;
        this.meaning = meaning;
        this.topicId = topicId;
        this.pronunciation = pronunciation;
        this.example = example;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
