package com.example.quizme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizme.utility.NetworkChangeListener;
import com.google.android.material.textfield.TextInputLayout;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditQuizDetailsActivity extends AppCompatActivity {

    EditText quizName,quizDuration,startDate,startTime;
    JSONObject json = null;
    String sDate,sTime,quizId;
    ImageButton calenderPicker,watch;
    DatePickerDialog.OnDateSetListener setListener;
    int hour,minute;
    String quiz_startTime,quiz_startDate,quiz_name,quiz_duration;
    TextInputLayout tName,tStartDate,tStartTime,tDuration;
    int quizID;

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
        setContentView(R.layout.aactivity_edit_quiz_details);

        Intent intent = getIntent();
        String quiz= intent.getExtras().getString("quiz",null);
        quizID = intent.getExtras().getInt("quizID");
        String name =  null,duration=null;
        try {
            json = new JSONObject(quiz);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            name = json.getString("name");
            duration = json.getString("duration");
            sDate = json.getString("startDate");
            sTime = json.getString("startTime");
            quizId = json.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        calenderPicker = findViewById(R.id.calender);
        watch = findViewById(R.id.watch);

        quizName = findViewById(R.id.editText_name);
        quizDuration = findViewById(R.id.editText_duration);
        startDate = findViewById(R.id.editText_startDate);
        startTime = findViewById(R.id.editText_startTime);


        tName = findViewById(R.id.outlinedTextField_name);
        tStartDate = findViewById(R.id.outlinedTextField_startDate);
        tStartTime = findViewById(R.id.outlinedTextField_startTime);
        tDuration = findViewById(R.id.outlinedTextField_duration);
        quizName.setText(name);
        quizDuration.setText(duration);
        startTime.setText(sTime);
        startDate.setText(sDate);

        Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);

        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize time picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(EditQuizDetailsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int in_hour, int in_minute) {
                        //initialize hour and minute
                        hour = in_hour;
                        minute = in_minute;
                        //initialize calender
                        Calendar calendar = Calendar.getInstance();
                        //set hour and minute
                        calendar.set(0,0,0,hour,minute);
                        //set selected time on text View
                        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
                        startTime.setText(format1.format(calendar.getTime()));
                        quiz_startTime = startTime.getText().toString().trim();
                        tStartTime.setError(null);
                        if(quiz_startTime.length() == 0){
                            tStartTime.setError("*Start Time is Required");
                        }
                        if (quiz_startTime.length() != 0 && !quiz_startTime.matches("\\d{2}:\\d{2}")) {
                            tStartTime.setError("*Start Time wrong format");
                        }
                    }
                },24,0,false);
                //display previous selected time
                timePickerDialog.updateTime(hour,minute);
                //show dialog
                timePickerDialog.show();
            }
        });



        calenderPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditQuizDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month += 1;
                        String date = year + "-" + month + "-"+day;
                        String newDate = year +"-"+((month<10)?"0"+month:month)+"-"+((day<10)?"0"+day:day);

                        quiz_startDate = newDate;
                        startDate.setText(quiz_startDate);
                        quiz_startDate = startDate.getText().toString().trim();
                        tStartDate.setError(null);
                        if(quiz_startDate.length() == 0){
                            tStartDate.setError("*Start Date is Required");
                        }
                        if (quiz_startDate.length() != 0 && !quiz_startDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            tStartDate.setError("*Start Date wrong format");
                        }
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        quizName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                quiz_name = quizName.getText().toString();
                if(quiz_name.trim().length() == 0){
                    tName.setError("*Quiz name is Required");
                }
                else{
                    tName.setError(null);
                }
            }
        });

        quizDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                quiz_duration = quizDuration.getText().toString();
                if(quiz_duration.trim().length() == 0){
                    tDuration.setError("*Duration is Required");
                }
                else{
                    tDuration.setError(null);
                }
            }
        });

    }

    public void goQuestions(View view) {
        Intent in = new Intent(this, EditQuestionActivity.class);
        in.putExtra("quizID",quizID);
        this.startActivity(in);
    }


    public void submitDetails(View view) {
        //get inputs
        quiz_name = quizName.getText().toString().trim();
        quiz_startTime = startTime.getText().toString().trim();
        quiz_startDate = startDate.getText().toString().trim();
        quiz_duration = quizDuration.getText().toString().trim();


        if(quiz_name.length() == 0){
            tName.setError("*Quiz name is Required");
        }
        if(quiz_startTime.length() == 0){
            tStartTime.setError("*Start Time is Required");
        }
        if(quiz_startDate.length() == 0){
            tStartDate.setError("*Start Date is Required");
        }
        if(quiz_duration.length() == 0){
            tDuration.setError("*Duration is Required");
        }
        if(quiz_duration.length() == 0){
            tDuration.setError("*Duration is Required");
        }
        if(quiz_startDate.length() == 0){
            tStartDate.setError("*Start Date is Required");
        }
        if(quiz_startTime.length() == 0){
            tStartTime.setError("*Start Time is Required");
        }
        if (quiz_startDate.length() != 0 && !quiz_startDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            tStartDate.setError("*Start Date wrong format");
        }
        if (quiz_startTime.length() != 0 && !quiz_startTime.matches("\\d{2}:\\d{2}")) {
            tStartTime.setError("*Start Time wrong format");
        }

        JSONObject data = new JSONObject();

        try {
            data.put("id", quizId);
            data.put("name",quiz_name);
            data.put("startTime",quiz_startTime);
            data.put("startDate",quiz_startDate);
            data.put("duration",quiz_duration);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("gkjnkh",data.toString());


    }


}
