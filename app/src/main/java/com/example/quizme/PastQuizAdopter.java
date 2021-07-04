package com.example.quizme;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class PastQuizAdopter extends RecyclerView.Adapter<PastQuizAdopter.ViewHolder> {

    ArrayList<PastQuizModel> pastQuizModels;
    Context context;

    public PastQuizAdopter(ArrayList<PastQuizModel> pastQuizModels, Context context) {
        this.pastQuizModels = pastQuizModels;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        holder.textViewDate.setText(pastQuizModels.get(position).getDate());
        holder.textViewTime.setText(pastQuizModels.get(position).getTime());
        holder.textViewName.setText(pastQuizModels.get(position).getQuizName());
        holder.textViewMarks.setText(pastQuizModels.get(position).getMarks());

    }

    @Override
    public int getItemCount() {
        return pastQuizModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName,textViewDate,textViewTime,textViewMarks;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_name);
            textViewDate = itemView.findViewById(R.id.text_date);
            textViewTime = itemView.findViewById(R.id.text_time);
            textViewMarks = itemView.findViewById(R.id.text_marks);
        }
    }
}
