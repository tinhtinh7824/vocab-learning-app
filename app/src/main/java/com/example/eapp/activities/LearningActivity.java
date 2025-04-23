package com.example.eapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eapp.R;
import com.example.eapp.TopicDetailActivity;
import com.example.eapp.adapters.TopicAdapter;
import com.example.eapp.database.AppDatabase;
import com.example.eapp.models.Topic;

import java.util.List;

public class LearningActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TopicAdapter topicAdapter;
    private AppDatabase db;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        // Lấy tham chiếu đến RecyclerView và nút Đăng xuất
        recyclerView = findViewById(R.id.recyclerViewTopics); // Sử dụng đúng id
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getInstance(this);  // Khởi tạo cơ sở dữ liệu

        // Thêm các chủ đề vào cơ sở dữ liệu nếu chưa có
        addDefaultTopicsToDatabase();

        // Thêm sự kiện cho nút Đăng xuất
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> showLogoutConfirmation());
    }

    // Thêm chủ đề mặc định vào cơ sở dữ liệu nếu bảng trống
    private void addDefaultTopicsToDatabase() {
        new Thread(() -> {
            // Lấy tất cả chủ đề từ database
            final List<Topic> topicList = db.topicDao().getAllTopics();

            // Nếu bảng trống thì chèn các chủ đề mặc định vào
            if (topicList.isEmpty()) {
                db.topicDao().insertTopic(new Topic("Trái cây", "10 từ về trái cây", R.drawable.trai_cay, 1, 10));  // Trái cây
                db.topicDao().insertTopic(new Topic("Động vật", "10 từ về động vật", R.drawable.dong_vat, 2, 10));  // Động vật
                db.topicDao().insertTopic(new Topic("Màu sắc", "10 từ về màu sắc", R.drawable.mau_sac, 3, 10));  // Màu sắc
                db.topicDao().insertTopic(new Topic("Số đếm", "10 số cơ bản", R.drawable.so_dem, 4, 10));  // Số đếm
                db.topicDao().insertTopic(new Topic("Nghề nghiệp", "10 nghề phổ biến", R.drawable.nghe_nghiep, 5, 10));  // Nghề nghiệp
                db.topicDao().insertTopic(new Topic("Giao thông", "10 phương tiện đi lại", R.drawable.giao_thong, 6, 10));  // Giao thông
            }

            // Lấy danh sách chủ đề mới nhất từ database sau khi chèn dữ liệu
            final List<Topic> updatedTopicList = db.topicDao().getAllTopics();

            // Cập nhật RecyclerView với danh sách chủ đề
            runOnUiThread(() -> {
                topicAdapter = new TopicAdapter(updatedTopicList, new TopicAdapter.TopicActionListener() {
                    @Override
                    public void onEditTopic(Topic topic) {
                        // Mở màn hình chi tiết của chủ đề khi người dùng nhấn vào chủ đề
                        Intent intent = new Intent(LearningActivity.this, TopicDetailActivity.class);
                        intent.putExtra("topicId", topic.getId()); // Truyền topicId sang Activity mới
                        startActivity(intent);
                    }
                });

                recyclerView.setAdapter(topicAdapter);
            });
        }).start();
    }

    // Hiển thị hộp thoại xác nhận đăng xuất
    private void showLogoutConfirmation() {
        new AlertDialog.Builder(this)
                .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                .setCancelable(false)
                .setPositiveButton("Có", (dialog, id) -> logout())
                .setNegativeButton("Không", null)
                .show();
    }

    // Đăng xuất và quay lại màn hình đăng nhập
    private void logout() {
        // Xóa thông tin userId trong SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userId");  // Xóa userId
        editor.apply();

        // Quay lại màn hình đăng nhập
        Intent intent = new Intent(LearningActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();  // Đóng LearningActivity
    }
}
