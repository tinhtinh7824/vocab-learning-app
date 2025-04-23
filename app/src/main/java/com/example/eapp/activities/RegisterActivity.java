package com.example.eapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eapp.R;
import com.example.eapp.database.AppDatabase;
import com.example.eapp.models.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPhone, etPassword, etConfirmPassword;
    private Button btnRegister;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Nút quay lại
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        db = AppDatabase.getInstance(this);  // sử dụng Room

        etUsername = findViewById(R.id.editTextUsername);
        etEmail = findViewById(R.id.editTextEmail);
        etPhone = findViewById(R.id.editTextPhone);
        etPassword = findViewById(R.id.editTextPassword);
        etConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnRegister = findViewById(R.id.buttonRegister);

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin bắt buộc.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không khớp.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 💡 Chạy kiểm tra + insert ở background thread
        new Thread(() -> {
            boolean exists = db.userDao().checkUserExists(username);

            runOnUiThread(() -> {
                if (exists) {
                    Toast.makeText(this, "Tên đăng nhập đã tồn tại.", Toast.LENGTH_SHORT).show();
                } else {
                    // Thêm vào DB
                    User user = new User(username, email, phone, password);
                    new Thread(() -> db.userDao().insertUser(user)).start();

                    Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    finish();  // hoặc chuyển qua LoginActivity nếu muốn
                }
            });
        }).start();
    }

}
