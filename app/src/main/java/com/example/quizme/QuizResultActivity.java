package com.example.quizme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.quizme.utility.NetworkChangeListener;

public class QuizResultActivity extends AppCompatActivity {

    TextView marks;
    TextView totalMarks;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);

        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);

        super.onStop();
    }

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