package com.example.quizme;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class EditQuestionadapter extends RecyclerView.Adapter<EditQuestionadapter.ViewHolder> {


    JSONArray problems;
    View view;
    Context context;



    public EditQuestionadapter( JSONArray problems, Context context) {
        this.problems = problems;
        this.context = context;
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

        EditQuestion tmpQuestion = null;
        try {
            int Qnum = position;
            tmpQuestion = new EditQuestion(
                    problems.getJSONObject(position).get("question").toString(),
                    Qnum,
                    problems.getJSONObject(position).getJSONArray("answers").get(0).toString(),
                    problems.getJSONObject(position).getJSONArray("answers").get(1).toString(),
                    problems.getJSONObject(position).getJSONArray("answers").get(2).toString(),
                    problems.getJSONObject(position).getJSONArray("answers").get(3).toString(),
                    (int)problems.getJSONObject(position).get("correctAnswer"),
                    problems.getJSONObject(position).get("image_url").toString()

            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.questionNumber.setText(String.valueOf(tmpQuestion.getQuestionNum() + 1));
        holder.question.setText(tmpQuestion.getQuestion().trim());
        holder.answer1.setText(tmpQuestion.getAnswer1().trim());
        holder.answer2.setText(tmpQuestion.getAnswer2().trim());
        holder.answer3.setText(tmpQuestion.getAnswer3().trim());
        holder.answer4.setText(tmpQuestion.getAnswer4().trim());


        holder.modifyQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(view.getContext(), EditQuizActivity.class);
                in.putExtra("quiz",problems.toString());
                Log.i("quiz",problems.toString());
                in.putExtra("Qnum",position);
                view.getContext().startActivity(in);
            }
        });



    }



    @Override
    public int getItemCount() {
        return problems.length();
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
        public Button finishQuiz;

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
            this.finishQuiz = itemView.findViewById(R.id.finishQuiz);

        }
    }


}
