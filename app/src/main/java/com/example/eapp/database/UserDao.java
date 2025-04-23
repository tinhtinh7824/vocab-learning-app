package com.example.eapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.eapp.models.User;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Query("SELECT COUNT(*) > 0 FROM users WHERE username = :username")
    boolean checkUserExists(String username);

    // Kiểm tra tài khoản và mật khẩu
    @Query("SELECT userId FROM users WHERE username = :username AND password = :password LIMIT 1")
    int validateUser(String username, String password);  // Trả về userId hợp lệ hoặc -1 nếu không tìm thấy
}

