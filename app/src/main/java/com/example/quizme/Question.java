package com.example.quizme;

import android.net.Uri;

import java.util.ArrayList;

public class Question {

    private String question;
    private int questionNum;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private Uri imageUri;
    private int correctAnswer;
    private ArrayList<String> answers = new ArrayList<>();

    public Question(String question, int questionNum, String answer1, String answer2, String answer3, String answer4, Uri imageUri,int correctAnswer) {
        this.question = question;
        this.questionNum = questionNum;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.imageUri = imageUri;
        this.correctAnswer = correctAnswer;
        setAnswerList();
    }

    public Question(String question, int questionNum, String answer1, String answer2, String answer3, String answer4,int correctAnswer) {
        this.question = question;
        this.questionNum = questionNum;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAnswer = correctAnswer;
        setAnswerList();
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
        setAnswerList();
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
        setAnswerList();
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
        setAnswerList();
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
        setAnswerList();
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setAnswerList(){
        this.answers.clear();
        this.answers.add(this.answer1);
        this.answers.add(this.answer2);
        this.answers.add(this.answer3);
        this.answers.add(this.answer4);
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }
}
