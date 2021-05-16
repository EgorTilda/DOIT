package com.example.doit;

import android.app.Application;

import androidx.room.Room;

import com.example.doit.database.AppDatabase;
import com.example.doit.database.TaskDao;
import com.example.doit.model.Task;

public class App extends Application {
    private AppDatabase db;
    private TaskDao taskDao;

    public static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-db")
                .fallbackToDestructiveMigration()
                .build();
        taskDao = db.taskDao();
        instance = this;
    }

    public AppDatabase getDb() {
        return db;
    }

    public void setDb(AppDatabase db) {
        this.db = db;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void insertTask(Task task) {
         Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
               taskDao.insert(task);
            }
        });
        thread.start();
    }

    public void deleteTask(Task task) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                taskDao.delete(task);
            }
        });
        thread.start();
    }

    public void updateTask(Task task) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                taskDao.update(task);
            }
        });
        thread.start();
    }
}
