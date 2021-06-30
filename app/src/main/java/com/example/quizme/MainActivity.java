package com.example.quizme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        String url = "https://quizmeonline.herokuapp.com/quiz/find/leaderboards";

        getPastQuizzes(url);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_quiz:
                            selectedFragment = new QuizFragment();
                            break;
                        case R.id.nav_leaderboard:
                            selectedFragment = new LBFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };


    private void getPastQuizzes(String URL){



        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String token=pref.getString("jwt",null);
        final String auth = "Bearer " + token;
        final String tkn = token;

        // Request a string response from the provided URL.
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        //Log.e("...Quizzes",response.toString());
                        //Toast.makeText(getContext(),response.toString(),Toast.LENGTH_SHORT).show();
                        //Log.e("response",response.toString());

                        JSONObject tmpLeaderBoard;
                        String title;
                        JSONArray leaderBoards;
                        String name;
                        JSONArray tmpMarkList;
                        SingleResult[] tmpResultList;
                        LeaderBoard[] tmpLdrBoard = new LeaderBoard[response.length()];
                        LeaderBoard tmpL;



                      try {



                            for(int i=0;i<response.length();i++){

                                tmpLeaderBoard = (JSONObject) response.get(i);
                                name = tmpLeaderBoard.getString("name");
                                Log.e("QuizName",name);
                                tmpMarkList = (JSONArray) tmpLeaderBoard.get("leaderboard");
                                tmpResultList = new SingleResult[tmpMarkList.length()];
                                SingleResult singleResult;
                                String user;
                                float mark;
                                JSONObject tmp;

                                for(int j=0;j<tmpMarkList.length();j++){

                                    tmp = (JSONObject)tmpMarkList.get(j);
                                    tmpResultList[j] = new SingleResult(tmp.getString("user"),(float)tmp.getDouble("marks"));
                                }


                                tmpLdrBoard[i] = new LeaderBoard(name,tmpResultList);


                            }

                            GlobalData.setLeaderBoards(tmpLdrBoard);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("regError",error.getMessage());


                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", auth);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

    }


}