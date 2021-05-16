package com.example.doit;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.paolorotolo.appintro.AppIntro2;

public class CustomIntro extends AppIntro2 {
    private void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onNextPressed() {
        // Do something here
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        // Здесь указываем количество слайдов, например нам нужно 3
        addSlide(Slide.newInstance(R.layout.intro_1)); //
        addSlide(Slide.newInstance(R.layout.intro_2));
        addSlide(Slide.newInstance(R.layout.intro_3));
    }

    @Override
    public void onDonePressed() {
        finish();
    }

    @Override
    public void onSlideChanged() {
        // Do something here
    }
}
