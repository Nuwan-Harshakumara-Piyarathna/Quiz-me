package com.example.quizme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        int status = intent.getIntExtra("status",0);

        QuizListAdapter adapter;
        ViewPager2 viewPager2 = findViewById(R.id.singleQ);
        if(status == 0) {
            adapter = new QuizListAdapter(GlobalData.getProblems(), status);
        }else{

            //creating dummy date
            ArrayList<Question> tmpQuestions = new ArrayList<>();
            Question tmpQuestion;
            for(int i=0;i<10;i++){
                tmpQuestion = new Question("Question "+String.valueOf(i+1),i,"answer1","answer2","answer3","answer4",1);
                tmpQuestions.add(tmpQuestion);
            }

            adapter = new QuizListAdapter(tmpQuestions, status);

        }
        viewPager2.setAdapter(adapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        /*RecyclerView recyclerView = findViewById(R.id.reView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);*/


    }
}