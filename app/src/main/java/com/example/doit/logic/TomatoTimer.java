package com.example.doit.logic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.doit.App;
import com.example.doit.MainViewModel;
import com.example.doit.R;

public class TomatoTimer {
    private final int STEP = 1000;
    private final int MILLIS = 60000;

    public TextView parentView;
    public int counter;
    public CountDownTimer timer;
    public boolean isRunning = false;
    public int minutes;
    public int seconds;
    public int time;
    public int timeBreak;
    public Context ctx;
    public LayoutInflater lnf;
    public Button doneBtn;
    public Button startBtn;

    public TomatoTimer(int _counter, TextView _parentView, int _time, int _timeBreak, Context _ctx, LayoutInflater _lnf, Button _doneBtn, Button _startBtn) {
        this.counter = _counter * MILLIS;
        this.time = _time;
        this.timeBreak = _timeBreak;
        this.parentView = _parentView;
        this.ctx = _ctx;
        this.lnf = _lnf;
        this.doneBtn = _doneBtn;
        this.startBtn = _startBtn;
    }

    public void start() {
        timer = new CountDownTimer(counter, STEP) {
            public void onTick(long millisUntilFinished) {
                counter = (int) millisUntilFinished;
                minutes = (counter / 1000) / 60;
                seconds = (counter / 1000) % 60;
                if(minutes == timeBreak && seconds == 0) {
                    isRunning = false;
                    alertTimeBreak();
                }
                String zeroMin = checkZero(minutes);
                String zeroSec = checkZero(seconds);
                parentView.setText(zeroMin + minutes + ":" + zeroSec + seconds);
            }
            public void onFinish() {
                reset();
                startBtn.setText("Начать");
            }
        };
        isRunning = true;
        timer.start();
    }

    public void reset() {
        timer.cancel();
        counter = time * MILLIS;
        isRunning = false;
        parentView.setText(time + ":" + "00");
    }

    public void cancel() {
        if(isRunning) {
            timer.cancel();
            isRunning = false;
        }
    }

    private String checkZero(int time) {
        if(time < 10 )
            return "0";
        else
            return  "";
    }

    public void alertTimeBreak() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        ConstraintLayout customAlertUi = (ConstraintLayout) lnf.inflate(R.layout.alert_tomato_break, null);
        builder.setView(customAlertUi);
        builder.setTitle("Время отдыха! Выполнили ли задачу?");
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { doneBtn.setEnabled(false); }
        });
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doneBtn.setEnabled(true);
            }
        });
        builder.show();
    }

}
