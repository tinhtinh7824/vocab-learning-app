package com.example.appta.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appta.R;
import com.example.appta.data.model.Topic;
import com.example.appta.ui.WordActivity;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    private Context context;
    private List<Topic> topicList;

    // Constructor
    public TopicAdapter(Context context, List<Topic> topicList) {
        this.context = context;
        this.topicList = topicList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item_topic
        View view = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Topic topic = topicList.get(position);
        holder.textTopic.setText(topic.getName());
        // Cập nhật icon nếu cần
        // holder.iconTopic.setImageResource(R.drawable.ic_topic);

        // Khi nhấn vào chủ đề, mở WordActivity với topicId
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, WordActivity.class);
            intent.putExtra("topicId", topic.getId());  // Chuyển topicId sang WordActivity
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    // ViewHolder chứa các view của mỗi item trong RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTopic;
        ImageView iconTopic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTopic = itemView.findViewById(R.id.textTopic);
            iconTopic = itemView.findViewById(R.id.iconTopic);
        }
    }
    public void updateTopic(int position, String newName) {
        Topic topic = topicList.get(position);
        topic.setName(newName); // Cập nhật tên chủ đề
        notifyItemChanged(position); // Thông báo cho RecyclerView biết rằng item tại vị trí này đã thay đổi
    }


}
