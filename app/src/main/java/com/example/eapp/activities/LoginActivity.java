package com.example.eapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.eapp.utils.NetworkUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eapp.R;
import com.example.eapp.database.AppDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // XML chứa nút và giao diện

        db = AppDatabase.getInstance(this);  // Khởi tạo cơ sở dữ liệu

        etUsername = findViewById(R.id.editTextLoginUsername);
        etPassword = findViewById(R.id.editTextLoginPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        ImageView btnBack = findViewById(R.id.btnBack);

        // Đăng nhập khi người dùng nhấn vào nút
        btnLogin.setOnClickListener(v -> loginUser());

        // Quay lại màn hình trước khi nhấn nút back
        btnBack.setOnClickListener(v -> finish());
    }

    private void loginUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "Không có kết nối mạng. Vui lòng kiểm tra lại.", Toast.LENGTH_SHORT).show();
            return;
        }
        // Kiểm tra nếu thông tin đăng nhập không trống
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Chạy xử lý đăng nhập trên background thread
        new Thread(() -> {
            // Kiểm tra thông tin tài khoản và lấy userId từ database
            int userId = db.userDao().validateUser(username, password);

            runOnUiThread(() -> {
                if (userId > 0) {  // Nếu userId hợp lệ (lớn hơn 0)
                    // Nếu đăng nhập hợp lệ, lưu userId vào SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("userId", userId); // Lưu userId
                    editor.apply();

                    // Thông báo đăng nhập thành công
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                    // Chuyển đến LearningActivity sau khi đăng nhập thành công
                    Intent intent = new Intent(LoginActivity.this, LearningActivity.class);
                    startActivity(intent);
                    finish();  // Đóng LoginActivity
                } else {  // Nếu tài khoản hoặc mật khẩu không đúng
                    // Thông báo đăng nhập không hợp lệ
                    Toast.makeText(this, "Sai tên đăng nhập hoặc mật khẩu.", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }


}
