package com.example.quizme;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PopUpSubmission extends Activity {


    Button home;
    Button copy;
    TextView quizId;
    static String quiz_link;
    static String startDate;
    static String startTime;
    static String duration;
    static String name;
    String message = "";

    private String genMessage(String link, String date, String time, String dur, String name) {
        return "Quizz: "+name+"\nLink for the Quiz: "+link+"\non: "+date+"\nat: "+time+"\nDuration: "+dur+" minutes";
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_submission);

        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.8));

        home = findViewById(R.id.gotohome);
        copy = findViewById(R.id.copytoclipboard);
        quizId = findViewById(R.id.quizId);
        quizId.setText("Quiz ID : " + quiz_link);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_SUBJECT, name);
                message = genMessage(quiz_link, startDate, startTime, duration, name);
                intentShare.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(intentShare, name));
//                ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
//                ClipData clip = ClipData.newPlainText("Copy Link", quiz_link);
//                clipboard.setPrimaryClip(clip);
//                clip.getDescription();
//                Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT);
            }
        });
    }


}
