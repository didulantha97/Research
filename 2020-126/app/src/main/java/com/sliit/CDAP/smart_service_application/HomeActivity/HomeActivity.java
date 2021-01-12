package com.sliit.CDAP.smart_service_application.HomeActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.sliit.CDAP.smart_service_application.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
    }

    /*
     * initialize the first fragment (ViewContactsFragment)
     */
    private void init() {
        ToolbarFragment fragment = new ToolbarFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back.
        transaction.replace(R.id.fragment_container, fragment);
//        transaction.addToBackStack(null); // Because we have only one fragment that why we haven't added this line. In case you have multiple fragment include this line.
        transaction.commit();
    }
}