package com.example.doit.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.doit.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAll();

    @Query("SELECT COUNT(*) FROM Task")
    LiveData<Integer> getCountTasks();

    @Query("SELECT * FROM Task WHERE status = 0")
    LiveData<List<Task>> getNotDoneAll();

    @Query("SELECT * FROM Task WHERE ID = :ID LIMIT 1")
    Task findByID(int ID);

    @Query("SELECT * FROM Task")
    List<Task> getAllTask();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}
