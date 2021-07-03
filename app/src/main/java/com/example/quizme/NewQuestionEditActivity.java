package com.example.quizme;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewQuestionEditActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);

        Intent in =  getIntent();
        final String mongoQid = in.getExtras().getString("quizId");


        //mongo quiz id
        Log.i("khfhljl;",mongoQid);


    }
}
