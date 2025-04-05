# Vocab Learning App

Ứng dụng học từ vựng tiếng Anh với các chức năng như:
- Đăng nhập / Đăng ký
- Flashcard từ vựng
- Kiểm tra trắc nghiệm
- Theo dõi tiến độ học tập

VocabLearningApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/vocabapp/
│   │   │   │   ├── activities/                 ← Màn hình chính của app
│   │   │   │   │   ├── LoginActivity.java           ← Đăng nhập
│   │   │   │   │   ├── RegisterActivity.java        ← Đăng ký
│   │   │   │   │   ├── MainActivity.java            ← Trang chính (danh sách chức năng)
│   │   │   │   │   ├── VocabListActivity.java       ← Danh sách từ vựng
│   │   │   │   │   ├── FlashcardActivity.java       ← Học từ bằng flashcard
│   │   │   │   │   ├── QuizActivity.java            ← Trắc nghiệm từ vựng
│   │   │   │   │   └── ProgressActivity.java        ← Thống kê tiến độ
│   │   │   │   ├── adapters/                   ← Adapter cho RecyclerView
│   │   │   │   │   ├── VocabAdapter.java
│   │   │   │   │   └── CourseAdapter.java
│   │   │   │   ├── models/                     ← Định nghĩa model
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── Vocab.java
│   │   │   │   │   └── Course.java
│   │   │   │   ├── database/                   ← SQLite Helper + DAO
│   │   │   │   │   ├── DatabaseHelper.java
│   │   │   │   │   └── VocabDAO.java
│   │   │   │   └── utils/                      ← Hàm tiện ích chung
│   │   │   │       ├── APIClient.java              ← Gọi API tra từ vựng
│   │   │   │       ├── ImageProcessor.java         ← Xử lý ảnh nếu có
│   │   │   │       └── SharedPrefManager.java      ← Lưu dữ liệu người dùng
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_login.xml
│   │   │   │   │   ├── activity_register.xml
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── activity_vocab_list.xml
│   │   │   │   │   ├── activity_flashcard.xml
│   │   │   │   │   ├── activity_quiz.xml
│   │   │   │   │   └── activity_progress.xml
│   │   │   │   ├── drawable/
│   │   │   │   │   └── bg_button.xml (nút tùy chỉnh)
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   └── styles.xml
│   │   │   │   └── mipmap/
│   │   │   │       └── ic_launcher.png (icon app)
│   │   │   └── AndroidManifest.xml
├── build.gradle
├── settings.gradle
└── README.md
