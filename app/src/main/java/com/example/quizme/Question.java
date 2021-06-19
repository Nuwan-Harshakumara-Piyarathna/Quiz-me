package com.example.quizme;

import android.net.Uri;

public class Question {

    private String question;
    private int questionNum;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int correctAns;
    private int clientAns;
    private Uri imageUri;

    public Question(String question, int questionNum, String answer1, String answer2, String answer3, String answer4, int correctAns) {
        this.question = question;
        this.questionNum = questionNum;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAns = correctAns;
    }

    public Question(String question, int questionNum, String answer1, String answer2, String answer3, String answer4, int correctAns, Uri imageUri) {
        this.question = question;
        this.questionNum = questionNum;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAns = correctAns;
        this.imageUri = imageUri;
    }

    public Question(String question, int questionNum, String answer1, String answer2, String answer3, String answer4, Uri imageUri) {
        this.question = question;
        this.questionNum = questionNum;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.imageUri = imageUri;
    }

    public Question(String question, int questionNum, String answer1, String answer2, String answer3, String answer4) {
        this.question = question;
        this.questionNum = questionNum;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;

    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public int getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(int correctAns) {
        this.correctAns = correctAns;
    }

    public int getClientAns() {
        return clientAns;
    }

    public void setClientAns(int clientAns) {
        this.clientAns = clientAns;
    }

    public boolean checkAnswer(){
        if(this.correctAns == this.clientAns) return true;
        return false;
    }

}
