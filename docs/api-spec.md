
# API Specification - Vocab Learning App

> File này mô tả danh sách API được sử dụng trong ứng dụng học từ vựng. Gồm các chức năng: đăng nhập, quản lý từ vựng, ôn tập, và yêu thích.

---

## Auth APIs

### Đăng ký
- **Endpoint:** `POST /auth/register`
- **Request:**
```json
{
  "name": "Nguyen Van A",
  "email": "abc@gmail.com",
  "password": "123456"
}
```
- **Response:**
```json
{
  "message": "Đăng ký thành công",
  "user_id": "abc123"
}
```

### Đăng nhập
- **Endpoint:** `POST /auth/login`
- **Request:**
```json
{
  "email": "abc@gmail.com",
  "password": "123456"
}
```
- **Response:**
```json
{
  "token": "jwt_token",
  "user_info": {
    "name": "Nguyen Van A",
    "id": "abc123"
  }
}
```

---

## Vocabulary APIs

### Lấy danh sách từ vựng
- **GET /vocab**
- **Response:**
```json
[
  {
    "id": "1",
    "word": "apple",
    "meaning": "quả táo",
    "pronunciation": "/ˈæp.əl/"
  }
]
```

### Lấy chi tiết từ
- **GET /vocab/{id}**

### Thêm từ mới
- **POST /vocab**
```json
{
  "word": "banana",
  "meaning": "quả chuối",
  "pronunciation": "/bəˈnɑː.nə/"
}
```

---

## Review APIs

### Lấy danh sách từ cần ôn
- **GET /review/{user_id}**

### Lưu kết quả ôn tập
- **POST /review**
```json
{
  "user_id": "abc123",
  "vocab_id": "5",
  "result": "correct"
}
```

---

## Favorite APIs

### Lấy danh sách từ yêu thích
- **GET /favorites/{user_id}**

### Thêm từ vào yêu thích
- **POST /favorites**
```json
{
  "user_id": "abc123",
  "vocab_id": "5"
}
```

### Xoá từ khỏi yêu thích
- **DELETE /favorites/{vocab_id}**

---

## Ghi chú
- Tất cả các API cần xác thực sẽ dùng token JWT trong Header:  
  ```
  Authorization: Bearer <token>
  ```

---


