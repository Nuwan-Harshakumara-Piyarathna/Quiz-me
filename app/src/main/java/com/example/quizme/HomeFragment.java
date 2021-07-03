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

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ViewGroup homeFrag = (ViewGroup)inflater.inflate(R.layout.home_fragment, container, false);
        Button qBtn = homeFrag.findViewById(R.id.quizTake);
        Button schBtn = homeFrag.findViewById(R.id.scheduleBtn);
        qBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PastQuizActivity.class);
                getContext().startActivity(intent);
            }
        });

        schBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HostedQuizActivity.class);
                getContext().startActivity(intent);
            }
        });
        return  homeFrag;
    }
}
