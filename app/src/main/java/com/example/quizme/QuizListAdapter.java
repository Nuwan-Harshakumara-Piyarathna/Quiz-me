package com.example.quizme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.ViewHolder>{


    ArrayList<Question> questions;

    public QuizListAdapter(ArrayList<Question> questions) {
        this.questions = questions;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View singleSch = layoutInflater.inflate(R.layout.single_question, parent, false);
        ViewHolder viewHolder = new ViewHolder(singleSch);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Question tmpQuestion = this.questions.get(position);

        holder.questionNumber.setText(String.valueOf(tmpQuestion.getQuestionNum()+1));
        holder.question.setText(tmpQuestion.getQuestion());
        if(tmpQuestion.getImageUri() != null) {

            holder.quizImage.setVisibility(View.VISIBLE);
            holder.quizImage.setImageURI(tmpQuestion.getImageUri());
        }

        holder.answer1.setText(tmpQuestion.getAnswer1());
        holder.answer2.setText(tmpQuestion.getAnswer2());
        holder.answer3.setText(tmpQuestion.getAnswer3());
        holder.answer4.setText(tmpQuestion.getAnswer4());


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


            public ViewHolder(View itemView) {
                super(itemView);

                this.questionNumber = itemView.findViewById(R.id.quizNum);
                this.question = itemView.findViewById(R.id.singleQus);
                this.quizImage = itemView.findViewById(R.id.quizImage);
                this.ans = itemView.findViewById(R.id.mcq);

                this.answer1 = itemView.findViewById(R.id.ans1);
                this.answer2 = itemView.findViewById(R.id.ans2);
                this.answer3 = itemView.findViewById(R.id.ans3);
                this.answer4 = itemView.findViewById(R.id.ans4);

            }
        }



}
