package com.example.quizme;

import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;


import android.view.View;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText fName,lName,userName,passwordOne,passwordTwo;
    private TextInputLayout fstName,lstName,user,passOne,passTwo;
    String fNameText,lNameText,userText,passText,cPassText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
        }else if(passText.length()<6 || passText.length()>15){
            passOne.setError("Password is too short");
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
    public void submitReg(View view) {

        if (!validateFields()) {
            return;
        }

        final String userName = userText;
        final String password = passText;
        final String fName = fNameText;
        final String lName = lNameText;

        WebRequest webRequest = new WebRequest(this);
        webRequest.execute(userName, password, fName, lName);
    }

    private class WebRequest extends AsyncTask<String,String,String> {

        Context con;

        public WebRequest(Context con) {
            this.con = con;
        }


        @Override
        protected String doInBackground(String... strings) {

            OkHttpClient client = new OkHttpClient();
            MediaType Json = MediaType.parse("application/json;charset=utf-8");
            JSONObject data = new JSONObject();
            String val = "";

            try {
                data.put("userName", strings[0]);
                data.put("password", strings[1]);
                data.put("firstName", strings[2]);
                data.put("lastName", strings[3]);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("data", data.toString());

            RequestBody body = RequestBody.create(data.toString(), Json);

            Request request = new Request.Builder().url(
                    "https://quizmeonline.herokuapp.com/all/registration"
            ).post(body).build();

            Response response = null;
            String responseBody = null;

            try {
                response = client.newCall(request).execute();
                responseBody = response.body().string();

            } catch (IOException e) {

                e.printStackTrace();
            }

            Log.i("res", responseBody);
            if (response.code() == 200) {

                if (responseBody.equals("userName is already exist")) {
                    return "UserName exists";
                } else {
                    return "OK";
                }

            }


            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s == null) {
                Toast toast = Toast.makeText(con, "Something Went Wrong Try Again Later!", Toast.LENGTH_SHORT);
                toast.show();
            } else if (s.equals("UserName exists")) {
                Toast toast = Toast.makeText(con, "User Name is already taken try with different User Name!", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                try {
                    Toast toast=Toast.makeText(con, "Registered Successfully", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(con, LoginActivity.class);
                    con.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}





