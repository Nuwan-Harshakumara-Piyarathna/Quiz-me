package com.example.quizme;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class HostedQuizAdopter extends RecyclerView.Adapter<HostedQuizAdopter.ViewHolder> {

    ArrayList<HostedQuizModel> hostedQuizModels;
    Context context;

    public HostedQuizAdopter(ArrayList<HostedQuizModel> hostedQuizModels, Context context) {
        this.hostedQuizModels = hostedQuizModels;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_quiz_recycle,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        holder.textViewDate.setText(hostedQuizModels.get(position).getDate());
        holder.textViewTime.setText(hostedQuizModels.get(position).getTime());
        holder.textViewName.setText(hostedQuizModels.get(position).getQuizName());
        holder.schBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(view.getContext(), EditQuizDetailsActivity.class);
                        in.putExtra("quiz",hostedQuizModels.get(position).getQuiz().toString());
                        in.putExtra("quizID",position);
                        view.getContext().startActivity(in);
                    }
        });

    }

    @Override
    public int getItemCount() {
        return hostedQuizModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName,textViewDate,textViewTime;
        Button schBtn;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_name);
            textViewDate = itemView.findViewById(R.id.text_date);
            textViewTime = itemView.findViewById(R.id.text_time);
            schBtn = itemView.findViewById(R.id.editBtn);
        }

    }
}
