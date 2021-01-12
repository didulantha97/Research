package com.sliit.CDAP.smart_service_application.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sliit.CDAP.smart_service_application.ChatActivity.MainChatActivity;
import com.sliit.CDAP.smart_service_application.MainActivity;
import com.sliit.CDAP.smart_service_application.R;

public class ActivityLogin extends AppCompatActivity {

    TextView loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (TextView) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityLogin.this, "Login successfully ..!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
