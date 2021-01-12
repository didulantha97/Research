package com.sliit.CDAP.smart_service_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.sliit.CDAP.smart_service_application.Activities.ChatFragment;
import com.sliit.CDAP.smart_service_application.Activities.JobFragment;
import com.sliit.CDAP.smart_service_application.Activities.OnlineFragment;
import com.sliit.CDAP.smart_service_application.Activities.ProfileFragment;
import com.sliit.CDAP.smart_service_application.Activities.ServicesFragmant;
import com.sliit.CDAP.smart_service_application.ChatActivity.MainChatActivity;
import com.sliit.CDAP.smart_service_application.Feedbacks.Feedbacks;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FloatingActionButton uButton;

    private int[] tabIcons = {
            R.drawable.icon_awesome_briefcase,
            R.drawable.icon_material_shopping_cart,
            R.drawable.icon_awesome_screwdriver,
            R.drawable.icon_ionic_ios_people,
            R.drawable.icon_material_settings

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        frameLayout=(FrameLayout)findViewById(R.id.frameLayout);
        uButton = (FloatingActionButton)findViewById(R.id.fab);

        fragment = new ChatFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        //tabLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.rectangle_12));

        uButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainChatActivity.class);
                startActivity(intent);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new JobFragment();
                        break;
                    case 1:
                        fragment = new ServicesFragmant();
                        break;
                    case 2:
                        fragment = new OnlineFragment();
                        break;
                    case 3:
                        fragment = new ProfileFragment();
                        break;
                    case 4:
                        fragment = new ChatFragment();
                        break;


                }

                setupTabIcons();

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();



            }

            public void setupTabIcons() {
                tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                tabLayout.getTabAt(2).setIcon(tabIcons[2]);
                tabLayout.getTabAt(3).setIcon(tabIcons[3]);
                tabLayout.getTabAt(4).setIcon(tabIcons[4]);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setupTabIcons();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                setupTabIcons();
            }

        });

    }
}
