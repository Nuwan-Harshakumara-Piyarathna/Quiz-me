package com.example.quizme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.ViewHolder>{


    ArrayList<Question> questions;
    View view;
    int status;


    public QuizListAdapter(ArrayList<Question> questions,int status) {
        this.questions = questions;
        this.status = status;
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

        holder.questionNumber.setText(String.valueOf(tmpQuestion.getQuestionNum()+1));
        holder.question.setText(tmpQuestion.getQuestion().trim());
        if(tmpQuestion.getImageUri() != null) {

            holder.quizImage.setVisibility(View.VISIBLE);
            holder.quizImage.setImageURI(tmpQuestion.getImageUri());
        }

        holder.answer1.setText(tmpQuestion.getAnswer1().trim());
        holder.answer2.setText(tmpQuestion.getAnswer2().trim());
        holder.answer3.setText(tmpQuestion.getAnswer3().trim());
        holder.answer4.setText(tmpQuestion.getAnswer4().trim());

        if (status == 1){
            holder.deleteQuestion.setVisibility(View.GONE);
            holder.modifyQuestion.setVisibility(View.GONE);
            holder.submitQuiz.setVisibility(View.GONE);
        }
        else {

            holder.deleteQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GlobalData.deleteQuestion(position);
                    GlobalData.reduceIndex(position);

                    Intent intent = new Intent(view.getContext(), CreateQuestionActivity.class);
                    view.getContext().startActivity(intent);
                    ((Activity)view.getContext()).finish();

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
                    ((Activity)view.getContext()).finish();

                }
            });

            holder.submitQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: Quiz submission



                }
            });
        }


    }
        @Override
        public int getItemCount () {
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

            }
        }



}
