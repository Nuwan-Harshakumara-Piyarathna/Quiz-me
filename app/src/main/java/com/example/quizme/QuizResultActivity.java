package com.example.quizme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class QuizResultActivity extends AppCompatActivity {

    TextView marks;
    TextView totalMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        marks = findViewById(R.id.marks);
        totalMarks = findViewById(R.id.totalMarks);

        marks.setText(String.valueOf(GlobalData.getMarks()));
        totalMarks.setText(String.valueOf(GlobalData.getLengthClient()));

    }
}