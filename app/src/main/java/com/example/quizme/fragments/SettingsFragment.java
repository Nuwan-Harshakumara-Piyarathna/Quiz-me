package com.example.quizme.fragments;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizme.R;
import com.example.quizme.InstructionsActivity;
import com.example.quizme.PrivacyPolicyActivity;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by chinthaka on 5/17/18.
 */

public class SettingsFragment extends Fragment {
    private View fragmentSettings;
    private LinearLayout  instructionsSelector, privacyPolicySelector, languageSelector;
    private ScrollView parentLayout;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSettings = inflater.inflate(R.layout.fragment_settings, container, false);
        // Get the widgets reference from XML layout
        parentLayout = fragmentSettings.findViewById(R.id.fragment_settings);
        instructionsSelector = fragmentSettings.findViewById(R.id.instructions_selector);
        privacyPolicySelector = fragmentSettings.findViewById(R.id.privacy_policy_selector);
        instructionsSelector.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InstructionsActivity.class);
                startActivity(intent);
            }
        });
        privacyPolicySelector.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PrivacyPolicyActivity.class);
                startActivity(intent);
            }
        });
        return fragmentSettings;
    }
}