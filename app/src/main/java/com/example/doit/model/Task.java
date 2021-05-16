package com.example.doit.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    public int ID;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "status")
   public boolean status;

    @ColumnInfo(name = "priority")
    public int priority;

    @ColumnInfo(name = "time")
    public long time;

    public Task(String text, int priority, boolean status, long time) {
        this.text = text;
        this.priority = priority;
        this.status = status;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return ID == task.ID &&
                status == task.status &&
                priority == task.priority &&
                Objects.equals(text, task.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, text, status, priority);
    }
}
