Create Database vocab_learning_app_data
use vocab_learning_app_data
-- Bảng Account
CREATE TABLE Account (
    userID CHAR(36) PRIMARY KEY,
    userName VARCHAR(100),
    email VARCHAR(100),
    passWord VARCHAR(100)
);

-- Bảng Word
CREATE TABLE Word (
    wordID CHAR(36) PRIMARY KEY,
    word VARCHAR(100),
    wordType VARCHAR(50),
    meaning TEXT,
    pronunciation VARCHAR(100),
    example TEXT
);

-- Bảng Question
CREATE TABLE Question (
    questionID CHAR(36) PRIMARY KEY,
    questionText VARCHAR(255),
    wordID CHAR(36) UNIQUE,
    FOREIGN KEY (wordID) REFERENCES Word(wordID)
);

-- Bảng Quiz
CREATE TABLE Quiz (
    quizID CHAR(36) PRIMARY KEY,
    quizName VARCHAR(100),
    duration VARCHAR(50),
    userID CHAR(36) UNIQUE,
    questionID CHAR(36),
    FOREIGN KEY (userID) REFERENCES Account(userID),
    FOREIGN KEY (questionID) REFERENCES Question(questionID)
);
-- Thêm dữ liệu vào bảng Account
INSERT INTO Account (userID, userName, email, passWord) VALUES
('1', 'Alice', 'alice@example.com', 'password123'),
('2', 'Bob', 'bob@example.com', 'password456'),
('3', 'Charlie', 'charlie@example.com', 'password789'),
('4', 'David', 'david@example.com', 'password101'),
('5', 'Eve', 'eve@example.com', 'password102');

-- Thêm dữ liệu vào bảng Word
INSERT INTO Word (wordID, word, wordType, meaning, pronunciation, example) VALUES
('1', 'run', 'verb', 'To move swiftly on foot.', '/rʌn/', 'She can run faster than anyone.'),
('2', 'apple', 'noun', 'A fruit from an apple tree.', '/ˈæp.əl/', 'He ate an apple after lunch.'),
('3', 'quickly', 'adverb', 'In a fast or quick manner.', '/ˈkwɪk.li/', 'She completed the task quickly.'),
('4', 'happy', 'adjective', 'Feeling or showing pleasure or contentment.', '/ˈhæp.i/', 'He felt happy with the results.'),
('5', 'beautiful', 'adjective', 'Pleasing to the senses or mind aesthetically.', '/ˈbjuː.tɪ.fəl/', 'The scenery was beautiful.');

-- Thêm dữ liệu vào bảng Question
INSERT INTO Question (questionID, questionText, wordID) VALUES
('1', 'What does the word "run" mean?', '1'),
('2', 'What is an example sentence using "apple"?', '2'),
('3', 'How would you describe someone doing something "quickly"?', '3'),
('4', 'What is the meaning of "happy"?', '4'),
('5', 'Can you provide an example of "beautiful"?', '5');

-- Thêm dữ liệu vào bảng Quiz
INSERT INTO Quiz (quizID, quizName, duration, userID, questionID) VALUES
('1', 'Vocabulary Quiz 1', '30 minutes', '1', '1'),
('2', 'Vocabulary Quiz 2', '25 minutes', '2', '2'),
('3', 'Adjective Quiz', '20 minutes', '3', '4'),
('4', 'Fruit Words Quiz', '15 minutes', '4', '2'),
('5', 'Verb Quiz', '30 minutes', '5', '1');