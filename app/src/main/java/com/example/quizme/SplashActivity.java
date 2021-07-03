package com.example.quizme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

        int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        status = 0;

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("baseURL", "http://quizme-env-1.eba-iz7bmwvh.us-east-1.elasticbeanstalk.com");
        editor.commit();

        CountDownTimer timer = new CountDownTimer(4000,10){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent intent;
                if(status == 0){
                intent = new Intent(SplashActivity.this,LoginActivity.class);

                }
                else{
                    intent = new Intent(SplashActivity.this,MainActivity.class);

                }
                startActivity(intent);
                finish();

            }
        };
        timer.start();


        String token = pref.getString("jwt",null);
        if(token != null) {
            WebRequest webRequest = new WebRequest(getApplicationContext());
            webRequest.execute();
        }

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        },3500);*/
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
            SharedPreferences pref = con.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            String baseURL =pref.getString("baseURL",null);
            String token = pref.getString("jwt",null);
            Log.i("token",token);

            try {
                data.put("jwt", token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("data", data.toString());

            RequestBody body = RequestBody.create(data.toString(), Json);




            String url = baseURL+"/all/is_token_expired";
            Log.d("URL : ",url);
            Request request = new Request.Builder().url(
                    url
            ).addHeader("Authorization","Bearer "+token).post(body).build();

            Response response = null;
            String responseBody = null;
            JSONObject json = null;

            try {
                response = client.newCall(request).execute();
                responseBody = response.body().string();
                val = responseBody;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(response.code()==200) {
                try {
                    //json = new JSONObject(responseBody);
                    //val = json.getString("jwt");
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
            if(s==null){
                Log.i("message","Something Went Wrong Try Again Later!");

            }
            else if(s.equals("false")){
                Log.i("loginStatus",s);

                        status = 1;
                    }
            else if(s.equals("true")){
                Log.i("loginStatus",s);
            }

            }





    }
}