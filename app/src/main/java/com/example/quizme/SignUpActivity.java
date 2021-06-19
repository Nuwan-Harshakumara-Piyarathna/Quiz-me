package com.example.quizme;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.quizme.databinding.ActivitySignUpBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText fName,lName,userName,passwordOne,passwordTwo;
    private TextInputLayout fstName,lstName,user,passOne,passTwo;
    String fNameText,lNameText,userText,passText,cPassText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //text field
        fName = findViewById(R.id.firstName);
        lName = findViewById(R.id.lastName);
        userName =findViewById(R.id.name);
        passwordOne = findViewById(R.id.passOne);
        passwordTwo = findViewById(R.id.passTwo);

        //layout field
        fstName = findViewById(R.id.fname);
        lstName = findViewById(R.id.lname);
        user = findViewById(R.id.loginUsername);
        passOne =findViewById(R.id.loginPassword);
        passTwo = findViewById(R.id.confirmPassword);

    }

    private boolean validateFields(){
        fNameText = fName.getText().toString();
        lNameText = lName.getText().toString();
        userText =  userName.getText().toString();
        passText = passwordOne.getText().toString();
        cPassText = passwordTwo.getText().toString();

        if(fNameText.isEmpty()){
            fstName.setError("First Name can't be Empty");
            return false;
        }else if(fNameText.length()<4 || fNameText.length()>10){
            fstName.setError("First Name should have at least 4 characters");
            return false;
        }

        if(lNameText.isEmpty()){
            lstName.setError("Last Name can't be Empty");
            return false;
        }else if(lNameText.length()<4 || lNameText.length()>10){
            lstName.setError("last Name should have at least 4 characters");
            return false;
        }

        if(userText.isEmpty()){
            user.setError("User Name can't be Empty");
            return false;
        }else if(lNameText.length()<4 || lNameText.length()>10){
            fstName.setError("User Name should have at least 4 characters");
            return false;
        }

        if(passText.isEmpty()){
            passOne.setError("Password can't be empty");
            return false;
        }

        if(cPassText.isEmpty()){
            passTwo.setError("Confirm Password can't be empty");
            return false;
        }else if(! passText.equals(cPassText)){
            passTwo.setError("Password is not matched");
            return false;
        }

        return true;
    }

}
