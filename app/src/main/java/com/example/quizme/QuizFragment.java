package com.example.quizme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class QuizFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View quizFrag = inflater.inflate(R.layout.quiz_fragment, container, false);

        Button qBtn =  (Button) quizFrag.findViewById(R.id.quizBtn);
        qBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), CreateQuestionActivity.class);
                in.putExtra("status",0);
                startActivity(in);
            }
        });

        Button takeQuiz = (Button) quizFrag.findViewById(R.id.takeQuiz);
        takeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GlobalData.removeAllClientQuestions();

                final View quizId = getLayoutInflater().inflate(R.layout.get_quiz_id,null);


                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                builder.setTitle("Enter Quiz Id");
                builder.setView(quizId);

                final EditText qId = quizId.findViewById(R.id.quizId);


                builder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String tmp = qId.getText().toString().trim();
                        if(tmp.length() == 0) {
                            Toast.makeText(getContext(), "Question Id is empty", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String Url = "https://quizmeonline.herokuapp.com/quiz/join/"+tmp;
                            String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhdGh1cmEiLCJleHAiOjE2MjQyOTcyNDIsImlhdCI6MTYyNDA4MTI0Mn0.87Li1pOc4xl1Iv7uz82veO38cN_MGHV6m7MP4mZDVshgEqq9W-WlCuxPQJc3_O_uApb5sH6ib9BobZSxXCDF-g";
                            getQuiz(Url,token);

                        }

                    }
                });



                builder.show();

            }
        });

        return  quizFrag;



    }


    private void getQuiz(String URL,String token){


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final String auth = "Bearer " + token;
        final String tkn = token;

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        //Log.e("stdres",response.toString());
                        //Toast.makeText(getContext(),response.toString(),Toast.LENGTH_SHORT).show();
                        //Log.e("response",response.toString());

                        JSONObject singleQuestion;
                        String title;
                        JSONArray answers;
                        int correctAnswer;


                        try {

                            JSONArray questions = (JSONArray) response.get("problems");
                            for(int i=0;i<questions.length();i++){

                                singleQuestion = (JSONObject) questions.get(i);
                                answers = (JSONArray) singleQuestion.get("answers");
                                correctAnswer = singleQuestion.getInt("correctAnswer");
                                title = singleQuestion.getString("question");
                                Log.e("title",title);
                                Question tmpQuestion = new Question(title,i,answers.getString(0),answers.getString(1),answers.getString(2),answers.getString(3),correctAnswer);
                                GlobalData.addClientQuestion(tmpQuestion);


                            }
                            Intent in = new Intent(getActivity(), HomeActivity.class);
                            in.putExtra("status",1);
                            startActivity(in);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.getMessage(), Toast.LENGTH_SHORT).show();


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
