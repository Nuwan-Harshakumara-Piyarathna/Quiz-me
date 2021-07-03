package com.example.quizme;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HostedQuizModel {

    String time;
    String date;
    String quizName;
    JSONObject quiz;


    public HostedQuizModel(String time, String date, String quizName,JSONObject quiz) {
        this.time = time;
        this.date = date;
        this.quizName = quizName;
        this.quiz = quiz;

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

    public JSONObject getQuiz() {
        return quiz;
    }

    public void setQuiz(JSONObject quiz) {
        this.quiz = quiz;
    }
}
