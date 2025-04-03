package com.example.vocablearningapp.models;

public class Word {
    private String wordID;
    private String word;
    private String wordType;
    private String meaning;
    private String pronunciation;
    private String example;
    private String topicID;

    public Word(String wordID, String word, String wordType, String meaning, String pronunciation, String example, String topicID) {
        this.wordID = wordID;
        this.word = word;
        this.wordType = wordType;
        this.meaning = meaning;
        this.pronunciation = pronunciation;
        this.example = example;
        this.topicID = topicID;
    }

    // Getter & Setter

    public String getWordID() {
        return wordID;
    }

    public void setWordID(String wordID) {
        this.wordID = wordID;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getTopicID() {
        return topicID;
    }

    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }
}
