package com.sliit.CDAP.smart_service_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sliit.CDAP.smart_service_application.ChatActivity.ImageReco.ImageReco;
import com.sliit.CDAP.smart_service_application.ChatActivity.MainChatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Start_Home extends AppCompatActivity {

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__home);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Start_Home.this, MainActivity.class);
                startActivity(intent);
            }
        }, 1000);

    }
}