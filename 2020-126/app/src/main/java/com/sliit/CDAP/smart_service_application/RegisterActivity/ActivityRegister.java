package com.sliit.CDAP.smart_service_application.RegisterActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sliit.CDAP.smart_service_application.ChatActivity.MainChatActivity;
import com.sliit.CDAP.smart_service_application.LoginActivity.ActivityLogin;
import com.sliit.CDAP.smart_service_application.R;

public class ActivityRegister extends AppCompatActivity {

    TextView registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerBtn = (TextView) findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityRegister.this, "Register successfully ..!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
                startActivity(intent);
            }
        });
    }
}
