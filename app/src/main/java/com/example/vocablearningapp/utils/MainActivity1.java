package com.example.appta.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appta.R;
import com.example.appta.adapter.TopicAdapter;
import com.example.appta.data.AppDatabase;
import com.example.appta.data.model.Topic;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase database;  // Khai báo đối tượng database
    private RecyclerView recyclerView;
    private TopicAdapter topicAdapter;
    private List<Topic> topicList;
    private ProgressBar progressBar;  // Khai báo thanh tiến trình

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lấy reference của ProgressBar từ layout XML
        progressBar = findViewById(R.id.progressBar);

        // Lấy instance của AppDatabase
        database = AppDatabase.getInstance(this);

        // Khởi tạo danh sách chủ đề
        topicList = new ArrayList<>();

        // Thiết lập RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo adapter và gán cho RecyclerView
        topicAdapter = new TopicAdapter(this, topicList);
        recyclerView.setAdapter(topicAdapter);

//        // Lấy reference của button
       Button btnAddTopic = findViewById(R.id.btnAddTopic);
       ImageView btnBack = findViewById(R.id.btnBack);

        // Xử lý sự kiện click vào nút để chèn chủ đề mới
        btnAddTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị thanh tiến trình khi bắt đầu thêm chủ đề
                progressBar.setVisibility(View.VISIBLE);

                // Tạo đối tượng Topic mới
                Topic newTopic = new Topic("Unit 1: My Family", "icon_url");

                // Chèn chủ đề vào database
                insertTopic(newTopic);
            }
        });

        // Xử lý sự kiện click vào nút back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện quay lại
                finish();
            }
        });

        // Tải dữ liệu từ database và hiển thị
        loadTopicsFromDatabase();
    }

    // Phương thức chèn dữ liệu vào database
    private void insertTopic(Topic topic) {
        // Thực hiện việc chèn dữ liệu vào database trong thread khác
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.topicDao().insertTopics(topic);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Ẩn thanh tiến trình sau khi chèn xong
                        progressBar.setVisibility(View.VISIBLE);

                        Toast.makeText(MainActivity.this, "Chủ đề đã được thêm!", Toast.LENGTH_SHORT).show();
                        // Cập nhật danh sách chủ đề và thông báo adapter
                        topicList.add(topic);
                        topicAdapter.notifyItemInserted(topicList.size() - 1);
                    }
                });
            }
        }).start();
    }

    // Phương thức xóa chủ đề khỏi cơ sở dữ liệu
    private void deleteTopic(Topic topic) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.topicDao().deleteTopic(topic);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Chủ đề đã được xóa!", Toast.LENGTH_SHORT).show();
                        // Cập nhật danh sách chủ đề và thông báo adapter
                        topicList.add(topic);
                        topicAdapter.notifyItemInserted(topicList.size() - 1);
                    }
                });
            }
        }).start();
    }
    // Phương thức cập nhật chủ đề trong cơ sở dữ liệu
    public void updateTopic(Topic topic, String newName) {
        new Thread(() -> {
            topic.setName(newName);  // Cập nhật tên chủ đề
            database.topicDao().updateTopic(topic);
            runOnUiThread(() -> {
                Toast.makeText(MainActivity.this, "Chủ đề đã được cập nhật!", Toast.LENGTH_SHORT).show();
                topicAdapter.notifyDataSetChanged();  // Thông báo RecyclerView cập nhật
            });
        }).start();
    }




    // Phương thức tải dữ liệu từ database và hiển thị
    private void loadTopicsFromDatabase() {
        // Hiển thị thanh tiến trình khi bắt đầu tải dữ liệu
        progressBar.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Lấy danh sách chủ đề từ database
                List<Topic> topics = database.topicDao().getAllTopics();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Ẩn thanh tiến trình sau khi tải xong
                        progressBar.setVisibility(View.VISIBLE);

                        // Cập nhật danh sách chủ đề và thông báo adapter
                        topicList.clear();
                        topicList.addAll(topics);
                        topicAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    // Phương thức tìm kiếm chủ đề (nếu có)
    private void searchTopics(String query) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Topic> topics = database.topicDao().searchTopics("%" + query + "%");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Cập nhật danh sách chủ đề và thông báo adapter
                        topicList.clear();
                        topicList.addAll(topics);
                        topicAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
