-- Tạo Database
CREATE DATABASE vocab_learning_app_data;
USE vocab_learning_app_data;

-- Bảng Account
CREATE TABLE Account (
    userID CHAR(36) PRIMARY KEY,
    userName VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    passWord VARCHAR(100) NOT NULL
);

-- Bảng Topic (Chủ đề)
CREATE TABLE Topic (
    topicID CHAR(36) PRIMARY KEY,
    topicName VARCHAR(100) NOT NULL
);

-- Bảng Word
CREATE TABLE Word (
    wordID CHAR(36) PRIMARY KEY,
    word VARCHAR(100) NOT NULL,
    wordType VARCHAR(50),
    meaning TEXT,
    pronunciation VARCHAR(100),
    example TEXT,
    topicID CHAR(36),
    FOREIGN KEY (topicID) REFERENCES Topic(topicID) ON DELETE SET NULL
);

-- Bảng Question
CREATE TABLE Question (
    questionID CHAR(36) PRIMARY KEY,
    questionText VARCHAR(255) NOT NULL,
    wordID CHAR(36) UNIQUE,
    FOREIGN KEY (wordID) REFERENCES Word(wordID) ON DELETE CASCADE
);

-- Bảng Quiz
CREATE TABLE Quiz (
    quizID CHAR(36) PRIMARY KEY,
    quizName VARCHAR(100) NOT NULL,
    duration VARCHAR(50)
);

-- Bảng trung gian Quiz_Question (Mỗi Quiz có nhiều Question)
CREATE TABLE Quiz_Question (
    quizID CHAR(36),
    questionID CHAR(36),
    PRIMARY KEY (quizID, questionID),
    FOREIGN KEY (quizID) REFERENCES Quiz(quizID) ON DELETE CASCADE,
    FOREIGN KEY (questionID) REFERENCES Question(questionID) ON DELETE CASCADE
);

-- Bảng Notification (Thông báo)
CREATE TABLE Notification (
    notificationID CHAR(36) PRIMARY KEY,
    userID CHAR(36),
    message TEXT NOT NULL,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userID) REFERENCES Account(userID) ON DELETE CASCADE
);

-- Bảng ReviewHistory (Lịch sử ôn tập)
CREATE TABLE ReviewHistory (
    reviewID CHAR(36) PRIMARY KEY,
    userID CHAR(36),
    wordID CHAR(36),
    reviewedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    score INT CHECK (score BETWEEN 0 AND 100),
    FOREIGN KEY (userID) REFERENCES Account(userID) ON DELETE CASCADE,
    FOREIGN KEY (wordID) REFERENCES Word(wordID) ON DELETE CASCADE
);

-- Bảng trung gian User_Quiz (Lưu danh sách người dùng tham gia quiz)
CREATE TABLE User_Quiz (
    userID CHAR(36),
    quizID CHAR(36),
    PRIMARY KEY (userID, quizID),
    FOREIGN KEY (userID) REFERENCES Account(userID) ON DELETE CASCADE,
    FOREIGN KEY (quizID) REFERENCES Quiz(quizID) ON DELETE CASCADE
);
