package com.example.vocablearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnGoToRegister;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        etUsername = findViewById(R.id.editTextLoginUsername);
        etPassword = findViewById(R.id.editTextLoginPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        btnGoToRegister = findViewById(R.id.buttonGoToRegister); // Thêm nút đăng ký

        btnLogin.setOnClickListener(v -> loginUser());

        btnGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.validateUser(username, password)) {
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            // TODO: Chuyển sang Activity chính
        } else {
            Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu sai.", Toast.LENGTH_SHORT).show();
        }
    }
}
