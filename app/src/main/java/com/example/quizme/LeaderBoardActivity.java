package com.example.quizme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class LeaderBoardActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        RecyclerView recyclerView = findViewById(R.id.lBRev);

        //collecting dummy data
        ArrayList<QuizResult> tmpData = new ArrayList<>();
        String[] names = {"John","Dev","Jason","Sam","Jim"};
        float[] marks = {88,50,60,90,75};
        QuizResult quizResult;
        for(int i=0;i<5;i++){
            quizResult = new QuizResult(names[i],marks[i]);
            tmpData.add(quizResult);
        }


        LeaderBoardAdapter adapter = new LeaderBoardAdapter (tmpData);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}