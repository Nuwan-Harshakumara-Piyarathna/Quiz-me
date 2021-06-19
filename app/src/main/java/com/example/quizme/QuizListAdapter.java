package com.example.quizme;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.ViewHolder> {


    ArrayList<Question> questions;
    View view;
    int status;
    Context context;
    int submitted;
    String quizLink;


    public QuizListAdapter(ArrayList<Question> questions, int status, Context context) {
        this.questions = questions;
        this.status = status;
        this.context = context;
        submitted = -1;
        quizLink = GlobalData.getLink();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View singleSch = layoutInflater.inflate(R.layout.single_question, parent, false);
        ViewHolder viewHolder = new ViewHolder(singleSch);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Question tmpQuestion = this.questions.get(position);

        holder.questionNumber.setText(String.valueOf(tmpQuestion.getQuestionNum() + 1));
        holder.question.setText(tmpQuestion.getQuestion().trim());
        if (tmpQuestion.getImageUri() != null) {

            holder.quizImage.setVisibility(View.VISIBLE);
            holder.quizImage.setImageURI(tmpQuestion.getImageUri());
        }



        holder.answer1.setText(tmpQuestion.getAnswer1().trim());
        holder.answer2.setText(tmpQuestion.getAnswer2().trim());
        holder.answer3.setText(tmpQuestion.getAnswer3().trim());
        holder.answer4.setText(tmpQuestion.getAnswer4().trim());
        if (position != this.questions.size() - 1) {
            holder.submitQuiz.setVisibility(View.GONE);
            holder.copyQuizLink.setVisibility(View.GONE);
        }

        if (status == 1) {
            holder.deleteQuestion.setVisibility(View.GONE);
            holder.modifyQuestion.setVisibility(View.GONE);
            holder.submitQuiz.setVisibility(View.GONE);
            holder.copyQuizLink.setVisibility(View.GONE);
        } else {

            holder.deleteQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GlobalData.deleteQuestion(position);
                    GlobalData.reduceIndex(position);

                    Intent intent = new Intent(view.getContext(), CreateQuestionActivity.class);
                    view.getContext().startActivity(intent);
                    ((Activity) view.getContext()).finish();

                }
            });

            holder.modifyQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GlobalData.setModifiedQuestion(questions.get(position));
                    GlobalData.deleteQuestion(position);
                    GlobalData.reduceIndex(position);

                    Intent intent = new Intent(view.getContext(), CreateQuestionActivity.class);
                    view.getContext().startActivity(intent);
                    ((Activity) view.getContext()).finish();

                }
            });

            holder.submitQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            doPostRequest();
                        }
                    }).start();

                }
            });

            holder.copyQuizLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Copy Link", quizLink);
                    clipboard.setPrimaryClip(clip);
                    clip.getDescription();
                    Toast.makeText(context, "Copied", Toast.LENGTH_SHORT);
                }
            });
        }


    }

    private void doPostRequest() {
        Log.d("Okhttp3:", "doPostRequest function called");
        String url = "https://quizmeonline.herokuapp.com/quiz/add";
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json;charset=utf-8");

        JSONArray questionsArray = new JSONArray();
        for (Question question : GlobalData.getProblems()) {
            JSONObject jo = new JSONObject();
            try {
                jo.put("question", question.getQuestion());
                JSONArray ja = new JSONArray(question.getAnswers());
                jo.put("answers", ja);
                jo.put("correctAnswer", question.getCorrectAnswer());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            questionsArray.put(jo);
        }

        JSONObject actualData = new JSONObject();

        try {
            actualData.put("name", GlobalData.getName());
            actualData.put("link", GlobalData.getLink());
            actualData.put("startDate", GlobalData.getStartDate());
            actualData.put("startTime", GlobalData.getStartTime());
            actualData.put("duration", GlobalData.getDuration());
            actualData.put("noOfProblems", Math.max(1, GlobalData.getNoOfProblems()));
            actualData.put("problems", questionsArray);
        } catch (JSONException e) {
            Log.d("Okhttp3:", "JSON Exception");
            e.printStackTrace();
        }


        RequestBody body = RequestBody.create(JSON, actualData.toString());
        Log.d("Okhttp3:", "Requestbody created");
        Log.d("Okhttp3:", "body = \n" + body.toString());
        Log.d("Okhttp3:", "actualData = \n" + actualData.toString());
        Request request = new Request.Builder()
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtdXVkaXlhIiwiZXhwIjoxNjI0MzI3NTUwLCJpYXQiOjE2MjQxMTE1NTB9.4QURscJkD4tAJy3p2DlrL0RUrULSMSbCvWRIA9QUax29DN4Q3LYawSPWXj6LrKN-oQ52VQSM3Dvu7LKwVd1GYw")
                .url(url)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.d("Okhttp3:", "request done, got the response");
            Log.d("Okhttp3:", response.body().string());


            submitted = 1;


            //clear Quiz data
            GlobalData.clear();

        } catch (IOException e) {
            Log.d("Okhttp3:", "IOEXCEPTION while request");
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView questionNumber;
        public TextView question;
        public ImageView quizImage;
        public RadioButton answer1;
        public RadioButton answer2;
        public RadioButton answer3;
        public RadioButton answer4;
        public RadioGroup ans;
        public Button deleteQuestion;
        public Button modifyQuestion;
        public Button submitQuiz;
        public Button copyQuizLink;


        public ViewHolder(View itemView) {
            super(itemView);

            view = itemView;

            this.questionNumber = itemView.findViewById(R.id.quizNum);
            this.question = itemView.findViewById(R.id.singleQus);
            this.quizImage = itemView.findViewById(R.id.quizImage);
            this.ans = itemView.findViewById(R.id.mcq);

            this.answer1 = itemView.findViewById(R.id.ans1);
            this.answer2 = itemView.findViewById(R.id.ans2);
            this.answer3 = itemView.findViewById(R.id.ans3);
            this.answer4 = itemView.findViewById(R.id.ans4);

            this.deleteQuestion = itemView.findViewById(R.id.delQus);
            this.modifyQuestion = itemView.findViewById(R.id.modQus);
            this.submitQuiz = itemView.findViewById(R.id.subQus);

            this.copyQuizLink = itemView.findViewById(R.id.copyLink);

        }
    }


}
