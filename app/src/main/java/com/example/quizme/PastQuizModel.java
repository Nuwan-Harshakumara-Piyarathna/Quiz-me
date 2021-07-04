package com.example.quizme;


public class PastQuizModel {

    String time;
    String date;
    String quizName;
    String marks;

    public PastQuizModel(String time, String date, String quizName,String marks) {
        this.time = time;
        this.date = date;
        this.quizName = quizName;
        this.marks = marks;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
