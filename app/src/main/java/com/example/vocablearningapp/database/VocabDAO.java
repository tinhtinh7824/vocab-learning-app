package com.example.vocablearningapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vocablearningapp.models.Word;

import java.util.ArrayList;
import java.util.List;

public class VocabDAO {
    private DatabaseHelper dbHelper;

    public VocabDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Hàm lấy tất cả từ vựng
    public List<Word> getAllWords() {
        List<Word> wordList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Word", null);

        if (cursor.moveToFirst()) {
            do {
                Word word = new Word(
                        cursor.getString(cursor.getColumnIndexOrThrow("wordID")),
                        cursor.getString(cursor.getColumnIndexOrThrow("word")),
                        cursor.getString(cursor.getColumnIndexOrThrow("wordType")),
                        cursor.getString(cursor.getColumnIndexOrThrow("meaning")),
                        cursor.getString(cursor.getColumnIndexOrThrow("pronunciation")),
                        cursor.getString(cursor.getColumnIndexOrThrow("example")),
                        cursor.getString(cursor.getColumnIndexOrThrow("topicID"))
                );
                wordList.add(word);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return wordList;
    }

    // Hàm lấy ngẫu nhiên n từ vựng
    public List<Word> getRandomWords(int count) {
        List<Word> allWords = getAllWords();
        List<Word> randomWords = new ArrayList<>();

        if (count >= allWords.size()) {
            return allWords; // nếu yêu cầu nhiều hơn số từ có sẵn
        }

        List<Integer> usedIndexes = new ArrayList<>();
        while (randomWords.size() < count) {
            int index = (int) (Math.random() * allWords.size());
            if (!usedIndexes.contains(index)) {
                usedIndexes.add(index);
                randomWords.add(allWords.get(index));
            }
        }

        return randomWords;
    }

    public void insertSampleWordsIfEmpty() {
        if (getAllWords().isEmpty()) {
            insertWord(new Word("1", "apple", "noun", "quả táo", "ˈæpl", "I ate an apple.", "1"));
            insertWord(new Word("2", "dog", "noun", "con chó", "dɒɡ", "The dog barked loudly.", "1"));
            insertWord(new Word("3", "book", "noun", "cuốn sách", "bʊk", "I read a book.", "1"));
            insertWord(new Word("4", "pen", "noun", "cây bút", "pen", "She has a red pen.", "1"));
            insertWord(new Word("5", "car", "noun", "xe hơi", "kɑːr", "He drives a fast car.", "1"));
            insertWord(new Word("6", "house", "noun", "ngôi nhà", "haʊs", "They live in a big house.", "1"));
            insertWord(new Word("7", "computer", "noun", "máy tính", "kəmˈpjuːtə", "I use a computer daily.", "1"));
        }
    }


    // Hàm thêm 1 từ vựng vào database
    public void insertWord(Word word) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("wordID", word.getWordID());
        values.put("word", word.getWord());
        values.put("wordType", word.getWordType());
        values.put("meaning", word.getMeaning());
        values.put("pronunciation", word.getPronunciation());
        values.put("example", word.getExample());
        values.put("topicID", word.getTopicID());

        db.insert("Word", null, values);
        db.close();
    }

    public void setFavorite(String wordID, boolean isFavorite) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isFavorite", isFavorite ? 1 : 0);
        db.update("Word", values, "wordID = ?", new String[]{wordID});
        db.close();
    }

    public void updateLastStudied(String wordID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("lastStudied", System.currentTimeMillis());
        db.update("Word", values, "wordID = ?", new String[]{wordID});
        db.close();
    }


    public List<Word> getFavoriteWords() {
        List<Word> favorites = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Word WHERE isFavorite = 1", null);

        if (cursor.moveToFirst()) {
            do {
                Word word = new Word(
                        cursor.getString(cursor.getColumnIndexOrThrow("wordID")),
                        cursor.getString(cursor.getColumnIndexOrThrow("word")),
                        cursor.getString(cursor.getColumnIndexOrThrow("wordType")),
                        cursor.getString(cursor.getColumnIndexOrThrow("meaning")),
                        cursor.getString(cursor.getColumnIndexOrThrow("pronunciation")),
                        cursor.getString(cursor.getColumnIndexOrThrow("example")),
                        cursor.getString(cursor.getColumnIndexOrThrow("topicID"))
                );
                favorites.add(word);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return favorites;
    }

    public List<Word> getRecentStudiedWords() {
        List<Word> wordList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Word WHERE lastStudied > 0 ORDER BY lastStudied DESC", null);

        if (cursor.moveToFirst()) {
            do {
                String wordID = cursor.getString(cursor.getColumnIndexOrThrow("wordID"));
                String word = cursor.getString(cursor.getColumnIndexOrThrow("word"));
                String meaning = cursor.getString(cursor.getColumnIndexOrThrow("meaning"));
                String pronunciation = cursor.getString(cursor.getColumnIndexOrThrow("pronunciation"));
                String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow("imageUrl"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                String example = cursor.getString(cursor.getColumnIndexOrThrow("example"));

                Word w = new Word(wordID, word, meaning, pronunciation, imageUrl, type, example);
                wordList.add(w);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return wordList;
    }



}
