package com.example.eapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.BaseAdapter;  // Sử dụng BaseAdapter thay vì ArrayAdapter

import com.example.eapp.R;
import com.example.eapp.models.TopicOption;

import java.util.List;

public class TopicOptionAdapter extends BaseAdapter {

    private Context context;
    private List<TopicOption> topicOptions;

    public TopicOptionAdapter(Context context, List<TopicOption> topicOptions) {
        this.context = context;
        this.topicOptions = topicOptions;
    }

    @Override
    public int getCount() {
        return topicOptions.size();
    }

    @Override
    public Object getItem(int position) {
        return topicOptions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_topic_option, parent, false);
        }

        TopicOption topicOption = topicOptions.get(position);

        TextView optionName = convertView.findViewById(R.id.optionName);
        optionName.setText(topicOption.getOptionName());

        return convertView;
    }
}
