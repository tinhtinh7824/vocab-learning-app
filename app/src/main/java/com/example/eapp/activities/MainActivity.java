package com.example.eapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eapp.R;
import com.example.eapp.database.DatabaseSeeder;

public class MainActivity extends AppCompatActivity {

    private Button loginButton, registerButton;
    private ImageView logoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        logoImageView = findViewById(R.id.logoImageView);

        // Gọi phương thức insertSampleWords() để chèn dữ liệu mẫu vào cơ sở dữ liệu
        DatabaseSeeder.insertSampleWords(this);  // Truyền context là MainActivity

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
