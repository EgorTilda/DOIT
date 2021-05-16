package com.example.doit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doit.model.Task;
import com.google.android.material.chip.ChipGroup;


public class TaskEditorFragment extends Fragment implements View.OnClickListener {

    public EditText editTaskText;
    public Button saveTaskBtn;
    public ChipGroup prioChipGroup;
    public int p = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_task_editor, container, false);

        editTaskText = (EditText) root.findViewById(R.id.editTaskText);
        saveTaskBtn = (Button) root.findViewById(R.id.saveTaskBtn);
        prioChipGroup = (ChipGroup) root.findViewById(R.id.prioritys);
        saveTaskBtn.setOnClickListener(this::onClick);

       prioChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.chip0:
                        p  = 5;
                        break;
                    case R.id.chip1:
                        p = 1;
                        break;
                    case R.id.chip2:
                        p  = 2;
                        break;
                    case R.id.chip3:
                         p = 3;
                        break;
                    case R.id.chip4:
                        p = 4;
                        break;
                }
            }
        });

        return root;
    }

    @Override
    public void onClick(View v) {
        if(editTaskText.getText().length() > 0 ) {
            App.getInstance().insertTask(new Task(editTaskText.getText().toString(), p, false, System.currentTimeMillis()));
            FragmentManager fm = getFragmentManager();
            fm.popBackStack();
       } else {
            Toast.makeText(getContext(), "Вы не ввели задачу! :)", Toast.LENGTH_SHORT).show();
        }

    }
}