package com.example.doit.ui.notifications;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.doit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotificationsFragment extends Fragment {

    private final int STEP_OF_TIME = 6;

    public EditText editTimeWork;
    public EditText timeBreakText;
    public Switch taskMethodSwitch;
    public FloatingActionButton savePrefsBtn;
    public FloatingActionButton minusTimeBtn;
    public FloatingActionButton plusTimeBtn;
    private SharedPreferences prefs;
    private boolean taskMethodState;
    private boolean isTaskMethod;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPrefs();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        editTimeWork = (EditText) root.findViewById(R.id.editTimeWork);
        timeBreakText = (EditText) root.findViewById(R.id.breakTime);
        taskMethodSwitch = (Switch) root.findViewById(R.id.taskMethod);
        savePrefsBtn  = (FloatingActionButton) root.findViewById(R.id.savePrefs);
        minusTimeBtn = (FloatingActionButton) root.findViewById(R.id.minusTime);
        plusTimeBtn = (FloatingActionButton) root.findViewById(R.id.plusTime);

        taskMethodState = true;
        taskMethodSwitch.setChecked(prefs.getBoolean("taskMethod", false));
        taskMethodState = false;

        editTimeWork.setText(prefs.getInt("tomatoTime", 30) + "");
        timeBreakText.setText(prefs.getInt("tomatoTimeBreak", 5) + "");


        minusTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(editTimeWork.getText().toString());
                if(Integer.parseInt(editTimeWork.getText().toString()) > 6) {
                    num -= STEP_OF_TIME;
                    editTimeWork.setText(num + "");
                    timeBreakText.setText(num / 6 + "");
                }
            }
        });

        plusTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(editTimeWork.getText().toString());
                if(Integer.parseInt(editTimeWork.getText().toString()) < 30) {
                    num += STEP_OF_TIME;
                    editTimeWork.setText(num + "");
                    timeBreakText.setText(num / 6 + "");
                }
            }
        });


        taskMethodSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!taskMethodState) {
                    isTaskMethod = isChecked;
                }
            }
        });

        savePrefsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTimeWork.getText().length() > 0) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("tomatoTime", Integer.parseInt(editTimeWork.getText().toString()));
                    editor.putInt("tomatoTimeBreak", Integer.parseInt(timeBreakText.getText().toString()));
                    editor.putInt("tomatoTimeWork", Integer.parseInt(editTimeWork.getText().toString()) -  (Integer.parseInt(editTimeWork.getText().toString()) / 6));
                    editor.putBoolean("taskMethod", isTaskMethod);
                    editor.apply();
                    Toast.makeText(getContext(), "Сохранено", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    public void initPrefs() {
        prefs = getContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
    }


}