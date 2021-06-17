package com.example.quizme;

import java.util.ArrayList;

public class GlobalData {

    private static ArrayList<Question> questionList = new ArrayList<>();


    public static ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public static void setQuestionList(ArrayList<Question> questionList) {
        GlobalData.questionList = questionList;
    }

    public static void addQuestion(Question question){

        questionList.add(question);

    }

    public static Question getQuestion(int index){
        return questionList.get(index);
    }

    public static void deleteQuestion(int index){

        questionList.remove(index);

    }

    public static void modifyQuestion(int index,Question question){

        questionList.set(index,question);

    }

    public static int getLength(){
        return questionList.size();
    }

}
