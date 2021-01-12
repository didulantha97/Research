package com.sliit.CDAP.smart_service_application.Activities;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.sliit.CDAP.smart_service_application.ChatActivity.MainChatActivity;
import com.sliit.CDAP.smart_service_application.LoginActivity.ActivityLogin;
import com.sliit.CDAP.smart_service_application.MainActivity;
import com.sliit.CDAP.smart_service_application.R;

public class ChatFragment extends Fragment {

    ImageView utilique;

    Intent intent;

    public ChatFragment() { }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_chat_fragment,
                container, false);

        ImageView utilique = (ImageView) view.findViewById(R.id.utilique);

        intent = new Intent(getActivity(), MainChatActivity.class);

        utilique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });


        return view;
    }



}
