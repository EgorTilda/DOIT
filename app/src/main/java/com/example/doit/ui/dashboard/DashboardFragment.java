package com.example.doit.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.doit.App;
import com.example.doit.MainViewModel;
import com.example.doit.R;
import com.example.doit.adapters.TomatoAdapter;
import com.example.doit.logic.TomatoTimer;
import com.example.doit.model.Task;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    public TextView count;
    public Button startPauseBtn;
    public Button resetBtn;
    public RecyclerView tomatoRv;
    public Button doneTaskBtn;

    public TomatoTimer timer;
    public SortedList<Task> listTasks;

    private SharedPreferences prefs;
    int counter;
    int timeBreak;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        counter = prefs.getInt("tomatoTime", 30);
        timeBreak = prefs.getInt("tomatoTimeBreak", 5);
        listTasks = new SortedList<>(Task.class, new SortedList.Callback<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if(o1.status && (!o2.status)) {
                    return 1;
                }
                if(o2.status && (!o1.status)) {
                    return -1;
                }
                return (int) (o1.time - o2.time);
            }

            @Override
            public void onChanged(int position, int count) {}

            @Override
            public boolean areContentsTheSame(Task oldItem, Task newItem) { return oldItem.equals(newItem); }

            @Override
            public boolean areItemsTheSame(Task item1, Task item2) {
                return  item1.ID == item2.ID;
            }

            @Override
            public void onInserted(int position, int count) {}

            @Override
            public void onRemoved(int position, int count) {}

            @Override
            public void onMoved(int fromPosition, int toPosition) {}
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        count = (TextView) root.findViewById(R.id.count);
        startPauseBtn = (Button) root.findViewById(R.id.startPauseBtn);
        resetBtn = (Button) root.findViewById(R.id.resetBtn);
        tomatoRv = (RecyclerView) root.findViewById(R.id.tomatoTasks);
        doneTaskBtn = (Button) root.findViewById(R.id.doneTaskBtn);

        doneTaskBtn.setEnabled(false);
        resetBtn.setEnabled(false);

        count.setText(counter + ":00");

        TomatoAdapter adapter = new TomatoAdapter();
        tomatoRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        tomatoRv.setAdapter(adapter);

        MainViewModel mvl = ViewModelProviders.of(this).get(MainViewModel.class);
        mvl.getTaskNotDoneData().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                for(Task item: tasks) {
                    listTasks.add(item);
                }
                adapter.setItems(tasks);
            }
        });



        timer = new TomatoTimer(counter, count, counter, timeBreak, getContext(), inflater, doneTaskBtn, startPauseBtn);
            startPauseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listTasks.size() > 0) {
                        if (!timer.isRunning) {
                            resetBtn.setEnabled(true);
                            timer.start();
                            startPauseBtn.setText("Пауза");
                        } else {
                            timer.cancel();
                            startPauseBtn.setText("Продолжить");
                        }
                    } else {
                        Toast.makeText(getContext(), "Задач нет :)", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPauseBtn.setText("Начать");
                timer.reset();
            }
        });

        doneTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listTasks.size() > 0) {
                    Task t = listTasks.get(0);
                    t.status = true;
                    App.getInstance().updateTask(t);
                    doneTaskBtn.setEnabled(false);
                }
            }
        });





        return root;
    }



}