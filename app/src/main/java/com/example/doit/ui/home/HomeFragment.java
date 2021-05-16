package com.example.doit.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doit.App;
import com.example.doit.R;
import com.example.doit.TaskEditorFragment;
import com.example.doit.adapters.TaskAdapter;
import com.example.doit.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.doit.MainViewModel;

import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    public FloatingActionButton addTaskBtn;
    public FragmentTransaction transaction;
    public RecyclerView rv;
    public LinearLayout ev;
    public int countTask;
    private boolean isMethodSix;
    private SharedPreferences userPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPreferences = getContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ev = (LinearLayout) root.findViewById(R.id.emptyView);
        rv = (RecyclerView) root.findViewById(R.id.taskListView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        addTaskBtn = (FloatingActionButton) root.findViewById(R.id.addTaskBtn);
        addTaskBtn.setOnClickListener(this::onClick);

        TaskAdapter adapter = new TaskAdapter();
        rv.setAdapter(adapter);

        MainViewModel mvl = ViewModelProviders.of(this).get(MainViewModel.class);
        mvl.getTaskLiveData().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if(tasks.isEmpty()) {
                    rv.setVisibility(View.GONE);
                    ev.setVisibility(View.VISIBLE);
                } else {
                    rv.setVisibility(View.VISIBLE);
                    ev.setVisibility(View.GONE);
                    adapter.setItems(tasks);
                }
            }
        });

        isMethodSix = userPreferences.getBoolean("taskMethod", false);
        App.getInstance().getTaskDao().getCountTasks().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                countTask = integer;
                if(isMethodSix && countTask >= 6) {
                    addTaskBtn.setEnabled(false);
                }
                if (!isMethodSix || countTask < 6) {
                    addTaskBtn.setEnabled(true);
                }
            }
        });


        return root;
    }

    @Override
    public void onClick(View v) {
        changeScreen();
    }

    public void changeScreen() {
        Fragment taskEditorFrag = new TaskEditorFragment();
        transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.nav_host_fragment, taskEditorFrag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void customAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        ConstraintLayout customAlertUi = (ConstraintLayout) getLayoutInflater().inflate(R.layout.alert_task_count, null);
        builder.setView(customAlertUi);
        builder.setTitle("Вы уверены?");
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

}