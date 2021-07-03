package com.example.quizme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.quizme.utility.NetworkChangeListener;
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
    LoadingDialog loadDialog;
    Button button;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("baseURL", "http://quizme-env.eba-iz7bmwvh.us-east-1.elasticbeanstalk.com");
        editor.commit();

        //text field
        userName = findViewById(R.id.name);
        password = findViewById(R.id.pass);

        //layout
        user = findViewById(R.id.loginUsername);
        pass = findViewById(R.id.loginPassword);
        button = findViewById(R.id.loginBtn);

    }
    public void goReg(View v){

        Intent intent = new Intent(this,SignUpActivity.class);
        this.startActivity(intent);

    }

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

        final String userName = userText;
        final String password = passText;

        loadDialog = new LoadingDialog(LoginActivity.this);
        loadDialog.startLoadingDialog();

        WebRequest webRequest = new WebRequest(this,loadDialog);
        webRequest.execute(userName,password);



    }

    private class WebRequest extends AsyncTask<String,String,String> {

        Context con;
        LoadingDialog ld;

        public WebRequest(Context con, LoadingDialog ld){
            this.con=con;
            this.ld=ld;
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



            SharedPreferences pref = con.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            String baseURL =pref.getString("baseURL",null);
            String url = baseURL+"/all/login";
            Log.d("URL : ",url);
            Request request = new Request.Builder().url(
                    url
            ).post(body).build();

            Response response = null;
            String responseBody = null;
            JSONObject json = null;

            try {
                response = client.newCall(request).execute();
                responseBody = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(responseBody.equals("user not found")){
                return "user not found";
            }
            else if(responseBody.equals("Incorrect userName or Password.")){
                return "Incorrect userName or Password.";
            }
            if(response.code()==200) {
                try {
                    json = new JSONObject(responseBody);
                    val = json.getString("jwt");
                } catch ( Exception e) {
                    e.printStackTrace();
                }
            }else{
                return null;
            }


            return val;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ld.dismissDialog();
            if(s==null){
                Toast toast=Toast.makeText(con, "Something Went Wrong Try Again Later!", Toast.LENGTH_SHORT);
                toast.show();
            }
            else if(s.equals("Incorrect userName or Password.")){
                Toast toast=Toast.makeText(con, "Incorrect userName or Password.", Toast.LENGTH_SHORT);
                toast.show();
            }
            else if(s.equals("user not found")){
                Toast toast=Toast.makeText(con, "New User?Sign UP", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {

                try {

                    SharedPreferences pref = con.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("jwt", s);
                    editor.commit();

                    //meken jwt gnin
                    pref = con.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    String jwt=pref.getString("jwt",null);
                    Log.i("jwt",jwt);
                    Intent intent = new Intent(con, MainActivity.class);
                    con.startActivity(intent);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }
}





