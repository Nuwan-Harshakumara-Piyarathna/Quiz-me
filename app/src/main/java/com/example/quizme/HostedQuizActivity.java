package com.example.quizme;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.quizme.utility.NetworkChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HostedQuizActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<HostedQuizModel> HostedQuizModels;
    HostedQuizAdopter HostedQuizAdopter;

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

        WebRequest webRequest = new WebRequest(this);
        webRequest.execute();

        setContentView(R.layout.hosted_quiz_activity);


    }

    private class WebRequest extends AsyncTask<String, String, String> {

        Context con;

        public WebRequest(Context con) {
            this.con = con;
        }


        @Override
        protected String doInBackground(String... strings) {



            SharedPreferences pref = con.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            String jwt = pref.getString("jwt", null);
            final String token = "Bearer " + jwt;

            OkHttpClient client = new OkHttpClient();
            MediaType Json = MediaType.parse("application/json;charset=utf-8");
            JSONObject data = new JSONObject();
            String val = "";

            RequestBody body = RequestBody.create(data.toString(), Json);

            okhttp3.Request request = new okhttp3.Request.Builder().url(
                    "https://quizmeonline.herokuapp.com/quiz/find/created/quizzes"
            ).header("Authorization", token).build();


            Response response = null;
            String responseBody = null;


            try {
                response = client.newCall(request).execute();
                responseBody = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response.code() == 200) {

                return responseBody;

            }
            return null;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("schedule",s);
            JSONObject json = null;
            JSONArray val=null;

            try {
                json = new JSONObject(s);
                val = json.getJSONArray("createdQuizzes");
            } catch ( Exception e) {
                e.printStackTrace();
            }



            recyclerView = findViewById(R.id.recycler_view);

            HostedQuizModels = new ArrayList<>();

            for (int i = 0; i < val.length(); i++) {

                HostedQuizModel model = null;
                try {
                    model = new HostedQuizModel(
                            val.getJSONObject(i).getString("startTime"),
                            val.getJSONObject(i).getString("startDate"),
                            val.getJSONObject(i).getString("name"),
                            val.getJSONObject(i)
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HostedQuizModels.add(model);
            }

            LinearLayoutManager layoutManager = new LinearLayoutManager(HostedQuizActivity.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            HostedQuizAdopter = new HostedQuizAdopter(HostedQuizModels, HostedQuizActivity.this);
            recyclerView.setAdapter(HostedQuizAdopter);


        }
    }
}




