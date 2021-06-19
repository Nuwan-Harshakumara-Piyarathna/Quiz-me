package com.example.quizme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText userName,password;
    private TextInputLayout user,pass;
    String userText,passText;

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //text field
        userName = findViewById(R.id.name);
        password = findViewById(R.id.pass);

        //layout
        user = findViewById(R.id.loginUsername);
        pass = findViewById(R.id.loginPassword);
        button = findViewById(R.id.loginBtn);
        
    }

    private boolean validateFields() {

        userText = userName.getText().toString();
        passText = password.getText().toString();

        if (userText.isEmpty()) {
            user.setError("User Name can't be Empty");
            return false;
        }

        if (passText.isEmpty()) {
            pass.setError("Password can't be Empty");
            return false;
        }

        return true;
    }

    public void submitLogin(View view){

        if(!validateFields()){
            return;
        }

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        final String userName = userText;
        final String password = passText;

        WebRequest webRequest = new WebRequest(this);
        webRequest.execute(userName,password);


    }

    private class WebRequest extends AsyncTask<String,String,String> {

        Context con;
        public WebRequest(Context con){
            this.con=con;
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
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("data", data.toString());

            RequestBody body = RequestBody.create(data.toString(), Json);

            Request request = new Request.Builder().url(
                    "https://quizmeonline.herokuapp.com/all/login"
            ).post(body).build();

            Response response = null;
            String responseBody = null;
            JSONObject json = null;

            try {
                response = client.newCall(request).execute();
                json = new JSONObject(response.body().string());

            } catch (IOException | JSONException e) {

                e.printStackTrace();
            }
            try {
                val = json.getString("jwt");
            } catch (JSONException e) {

                e.printStackTrace();
            }


            return val;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                SharedPreferences pref = con.getSharedPreferences("MyPreferences",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("jwt",s);
                editor.commit();
                Intent intent = new Intent(con,MainActivity.class);
                con.startActivity(intent);


            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}





