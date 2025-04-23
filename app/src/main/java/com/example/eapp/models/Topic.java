package com.example.eapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "topics")
public class Topic {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String description;
    private int iconResourceId;
    private int topicId;
    private int wordCount;  // Thêm trường này để lưu số lượng từ vựng

    // Constructor
    public Topic(String name, String description, int iconResourceId, int topicId, int wordCount) {
        this.name = name;
        this.description = description;
        this.iconResourceId = iconResourceId;
        this.topicId = topicId;
        this.wordCount = wordCount;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public int getId() {
        return id;
    }

    public int getTopicId() {
        return topicId;
    }

    public int getWordCount() {
        return wordCount;  // Getter for wordCount
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;  // Setter for wordCount
    }

    // Setters for other fields
    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIconResourceId(int iconResourceId) {
        this.iconResourceId = iconResourceId;
    }
}
