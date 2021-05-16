package com.example.doit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.doit.model.Task;

import java.util.List;

public class MainViewModel extends ViewModel {
    private LiveData<List<Task>> TaskLiveData = App.getInstance().getTaskDao().getAll();
    private LiveData<List<Task>> TaskDoneData = App.getInstance().getTaskDao().getNotDoneAll();
    public LiveData<List<Task>> getTaskLiveData() {
        return TaskLiveData;
    }
    public LiveData<List<Task>> getTaskNotDoneData() {
        return TaskDoneData;
    }
}
