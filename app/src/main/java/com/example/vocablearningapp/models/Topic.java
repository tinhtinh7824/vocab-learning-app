package com.example.appta.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "topics")  // Đặt tên bảng là "topics"
public class Topic {

    @PrimaryKey(autoGenerate = true)  // Tự động tăng ID cho mỗi chủ đề
    private int id;

    private String name;   // Tên chủ đề
    private String icon;   // Icon của chủ đề

    // Constructor
    public Topic(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    // Getter and Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
