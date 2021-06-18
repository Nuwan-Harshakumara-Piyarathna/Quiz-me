package com.example.quizme;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
                Intent in = new Intent(getActivity(), HomeActivity.class);
                in.putExtra("status",1);
                startActivity(in);
            }
        });

        return  quizFrag;



    }

}
