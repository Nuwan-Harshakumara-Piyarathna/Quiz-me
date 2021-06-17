package com.example.quizme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CreateQuestionActivity extends AppCompatActivity {

    ImageView questionImage;
    Button pickImage;
    Button addQuestion;
    Button viewQuestions;
    Button questionCount;
    Button deleteImage;
    EditText questionTitle;
    EditText ans1;
    EditText ans2;
    EditText ans3;
    EditText ans4;
    Uri imageUri;

    private static final int PICK_IMAGE = 100;
    //private  static  final  int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        questionImage = findViewById(R.id.quesImg);
        pickImage = findViewById(R.id.pickImage);
        viewQuestions = findViewById(R.id.viewList);
        addQuestion = findViewById(R.id.addQuestion);
        questionTitle = findViewById(R.id.qId);
        questionCount = findViewById(R.id.questionCount);
        deleteImage = findViewById(R.id.deleteImage);
        ans1 = findViewById(R.id.qA1);
        ans2 = findViewById(R.id.qA2);
        ans3 = findViewById(R.id.qA3);
        ans4 = findViewById(R.id.qA4);

        questionCount.setText("Question Count : "+GlobalData.getLength());

        if(GlobalData.getModifiedQuestion() != null){

            Question tmpQuestion = GlobalData.getModifiedQuestion();
            GlobalData.setModifiedQuestion(null);

            questionTitle.setText(tmpQuestion.getQuestion());
            questionImage.setImageURI(tmpQuestion.getImageUri());
            ans1.setText(tmpQuestion.getAnswer1());
            ans2.setText(tmpQuestion.getAnswer2());
            ans3.setText(tmpQuestion.getAnswer3());
            ans4.setText(tmpQuestion.getAnswer4());
            imageUri = tmpQuestion.getImageUri();

        }

        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openGallery();

            }
        });

        viewQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(GlobalData.getLength()>0) {
                    Intent intent = new Intent(CreateQuestionActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                questionImage.setImageURI(null);
                imageUri = null;

            }
        });

        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Question tmpQuestion ;
                String title = questionTitle.getText().toString();
                String answer1 = ans1.getText().toString();
                String answer2 = ans2.getText().toString();
                String answer3 = ans3.getText().toString();
                String answer4 = ans4.getText().toString();



                Log.e("ques",questionTitle.getText().toString());

                if(title.trim().length() == 0){
                    //questionTitle.requestFocus();
                    questionTitle.setError("Question name can not be empty");
                }
                if(answer1.trim().length() == 0){
                    //ans1.requestFocus();
                    ans1.setError("Answer1 can not be empty");
                }
                if(answer2.trim().length() == 0){
                    //ans2.requestFocus();
                    ans2.setError("Answer2 can not be empty");
                }
                if(answer3.trim().length() == 0){
                    //ans3.requestFocus();
                    ans3.setError("Answer3 can not be empty");
                }
                if(answer4.trim().length() == 0){
                   // ans4.requestFocus();
                    ans4.setError("Answer4 can not be empty");
                }


                if(title.trim().length() != 0 && answer1.trim().length() != 0 && answer2.trim().length() != 0 && answer3.trim().length() != 0 && answer4.trim().length() != 0 ) {

                    if (imageUri != null) {
                        tmpQuestion = new Question(title, GlobalData.getLength(), answer1, answer2, answer3, answer4, imageUri);

                    } else {
                        tmpQuestion = new Question(title, GlobalData.getLength(), answer1, answer2, answer3, answer4);

                    }

                    GlobalData.addQuestion(tmpQuestion);
                    questionCount.setText("Question Count : "+GlobalData.getLength());
                    questionImage.setImageURI(null);
                    imageUri = null;
                    questionTitle.setText("");
                    ans1.setText("");
                    ans2.setText("");
                    ans3.setText("");
                    ans4.setText("");
                   // Log.e("sample question", GlobalData.getQuestion(0).getImageUri().toString());


                }

            }
        });
    }

    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){

            imageUri = data.getData();
            questionImage.setImageURI( imageUri);

        }
    }
}