package com.example.quizme;

import java.util.ArrayList;

public class GlobalData {

    private static String name;//quiz name
    private static String link;//quiz link
    private static String startDate;//yyyy-MM-dd format
    private static String startTime;//HH:mm format
    private static String duration;//in minutes
    private static int noOfProblems;
    private static ArrayList<Question> problems = new ArrayList<>();
    private static Question modifiedQuestion;
    public static ArrayList<Question> clientQuestions = new ArrayList<>();

    public static Question getModifiedQuestion() {
        return modifiedQuestion;
    }

    public static void setModifiedQuestion(Question modifiedQuestion) {
        GlobalData.modifiedQuestion = modifiedQuestion;
    }

    public static ArrayList<Question> getProblems() {
        return problems;
    }

    public static void setProblems(ArrayList<Question> problems) {
        GlobalData.problems = problems;
        setNoOfProblems(problems.size());
    }

    public static void addQuestion(Question question){

        problems.add(question);

    }

    public static Question getQuestion(int index){
        return problems.get(index);
    }

    public static void deleteQuestion(int index){

        problems.remove(index);

    }

    public static void modifyQuestion(int index,Question question){

        problems.set(index,question);

    }

    public static void modifyClientQuestion(int index,Question question){

        clientQuestions.set(index,question);

    }

    public static int getLength(){
        return problems.size();
    }

    public  static  int getLengthClient(){
        return  clientQuestions.size();
    }

    public static void reduceIndex(int index){
        Question tmpQuestion;

        for(int i = index; i< problems.size(); i++){

            tmpQuestion = problems.get(i);
            tmpQuestion.setQuestionNum(i);
            problems.set(i,tmpQuestion);
        }
    }

    public static String getName() {
        return name;
    }

    public static void setName(String input_name) {
        name = input_name;
    }

    public static String getLink() {
        return link;
    }

    public static void setLink(String input_link) {
        link = input_link;
    }

    public static String getStartDate() {
        return startDate;
    }

    public static void setStartDate(String input_startDate) {
        startDate = input_startDate;
    }

    public static String getStartTime() {
        return startTime;
    }

    public static void setStartTime(String input_startTime) {
        startTime = input_startTime;
    }

    public static String getDuration() {
        return duration;
    }

    public static void setDuration(String input_duration) {
        duration = input_duration;
    }

    public static int getNoOfProblems() {
        return noOfProblems;
    }

    public static void setNoOfProblems(int no) {
        noOfProblems = no;
    }

    public static void clear(){
        name = "";
        link = "";
        startDate = "";
        startTime = "";
        duration = "";
        noOfProblems = 0;
        problems = new ArrayList<>();
        modifiedQuestion = null;
    }
    public static void addClientQuestion(Question question){
        clientQuestions.add(question);
    }

    public static ArrayList<Question> getClientQuestions(){
        return clientQuestions;
    }

    public static void removeAllClientQuestions(){
        clientQuestions.clear();
    }

    public  static int getMarks(){
        int count =0;

        for(int i = 0;i<clientQuestions.size();i++){

            Question tmp = clientQuestions.get(i);
            if(tmp.getClientAns() == tmp.getCorrectAns()) count++;

        }
        return count;
    }

}
