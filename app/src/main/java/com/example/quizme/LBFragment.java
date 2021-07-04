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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LBFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup lbFrag = (ViewGroup)inflater.inflate(R.layout.lb_fragment, container, false);

        RecyclerView recyclerView = lbFrag.findViewById(R.id.qNamesRev);

        String[] names = new String[GlobalData.getLeaderBoardLength()];

        QuizResult quizResult;
        for(int i=0;i<GlobalData.getLeaderBoardLength();i++){
            names[i] = GlobalData.getLeaderBoardName(i);
        }


        QuizNameAdapter adapter = new QuizNameAdapter (names);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return  lbFrag;



    }
}
