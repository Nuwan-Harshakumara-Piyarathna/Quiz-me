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


public class PastQuizActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<PastQuizModel> pastQuizModels;
    PastQuizAdopter pastQuizAdopter;

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

        PastQuizActivity.WebRequest webRequest = new WebRequest(this);
        webRequest.execute();

        setContentView(R.layout.activity_past_quiz);


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
                    "https://quizmeonline.herokuapp.com/quiz/find/past/quizzes"
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

                JSONObject json = null;
                JSONArray val=null;

                try {
                    json = new JSONObject(s);
                    val = json.getJSONArray("pastQuizzes");
                } catch ( Exception e) {
                    e.printStackTrace();
                }



            recyclerView = findViewById(R.id.recycler_view);

            pastQuizModels = new ArrayList<>();

            for (int i = 0; i < val.length(); i++) {

                PastQuizModel model = null;
                try {
                    model = new PastQuizModel(
                            val.getJSONObject(i).getString("time"),
                            val.getJSONObject(i).getString("date"),
                            val.getJSONObject(i).getString("name"),
                            val.getJSONObject(i).getString("marks")
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pastQuizModels.add(model);
            }

            LinearLayoutManager layoutManager = new LinearLayoutManager(PastQuizActivity.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            pastQuizAdopter = new PastQuizAdopter(pastQuizModels, PastQuizActivity.this);
            recyclerView.setAdapter(pastQuizAdopter);


        }
    }
}




