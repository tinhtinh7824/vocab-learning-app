package com.example.eapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import com.example.eapp.R;
import com.example.eapp.models.UserQuizResult;

import java.util.List;

public class QuizHistoryAdapter extends ArrayAdapter<UserQuizResult> {

    private List<UserQuizResult> quizHistoryList;

    public QuizHistoryAdapter(Context context, List<UserQuizResult> quizHistoryList) {
        super(context, 0, quizHistoryList);
        this.quizHistoryList = quizHistoryList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_quiz_history, parent, false);
        }

        UserQuizResult result = quizHistoryList.get(position);

        TextView topicName = convertView.findViewById(R.id.topicName);
        TextView score = convertView.findViewById(R.id.score);
        TextView date = convertView.findViewById(R.id.date);

        topicName.setText(result.getTopicName());
        score.setText("Điểm: " + result.getScore());
        date.setText("Ngày: " + result.getDate());

        return convertView;
    }
}
