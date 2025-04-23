package com.example.eapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eapp.R;
import com.example.eapp.models.Topic;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private List<Topic> topicList;
    private TopicActionListener topicActionListener;

    public TopicAdapter(List<Topic> topicList, TopicActionListener topicActionListener) {
        this.topicList = topicList;
        this.topicActionListener = topicActionListener;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic topic = topicList.get(position);
        holder.topicName.setText(topic.getName());
        holder.wordCount.setText("Số từ vựng: " + topic.getWordCount());

        // Cập nhật ảnh cho mỗi chủ đề
        holder.imageTopic.setImageResource(topic.getIconResourceId());

        holder.itemView.setOnClickListener(v -> {
            if (topicActionListener != null) {
                topicActionListener.onEditTopic(topic);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder {

        private TextView topicName;
        private TextView wordCount;
        private ImageView imageTopic;

        public TopicViewHolder(View itemView) {
            super(itemView);
            topicName = itemView.findViewById(R.id.topicName);
            wordCount = itemView.findViewById(R.id.wordCount);
            imageTopic = itemView.findViewById(R.id.imageTopic);  // Lấy ImageView
        }
    }

    public interface TopicActionListener {
        void onEditTopic(Topic topic);
    }
}
