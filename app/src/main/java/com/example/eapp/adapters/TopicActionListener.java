package com.example.eapp.adapters;

import com.example.eapp.models.Topic;

public interface TopicActionListener {
    void onEditTopic(Topic topic);
    void onDeleteTopic(Topic topic);
}
