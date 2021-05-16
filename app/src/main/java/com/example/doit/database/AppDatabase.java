package com.example.doit.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.doit.model.Task;

@Database(entities = {Task.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
