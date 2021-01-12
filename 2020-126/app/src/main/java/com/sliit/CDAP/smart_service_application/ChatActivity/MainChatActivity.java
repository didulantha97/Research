package com.sliit.CDAP.smart_service_application.ChatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sliit.CDAP.smart_service_application.ChatActivity.ImageReco.ImageReco;
import com.sliit.CDAP.smart_service_application.Feedbacks.Feedbacks;
import com.sliit.CDAP.smart_service_application.LoginActivity.ActivityLogin;
import com.sliit.CDAP.smart_service_application.MainActivity;
import com.sliit.CDAP.smart_service_application.Models.message;
import com.sliit.CDAP.smart_service_application.Models.service;
import com.sliit.CDAP.smart_service_application.R;
import com.sliit.CDAP.smart_service_application.ServiceRequestActivity;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;


public class MainChatActivity extends AppCompatActivity implements AIListener {

    private static final String TAG = "ChatActivity";

    private ChatArrayAdapter chatArrayAdapter;

    List list;

    private DatabaseReference mDatabase;
    long maxid = 0;

    private ListView listView;
    //private TextView dialog;
    private ImageButton buttonVoice;
    private ImageView voice_bot, backbot;

    //Declare data types to use in Service request
    public String category, subCategory, description, priority, longitude, latitude;

    TextToSpeech t1;

    AIService aiService;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private final int REQ_CODE = 100;

    private boolean side = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        //dialog = (TextView) findViewById(R.id.dialog);
        buttonVoice = (ImageButton) findViewById(R.id.voice_btn);
        voice_bot = (ImageView) findViewById(R.id.voice_bot);
        backbot = (ImageView) findViewById(R.id.backbot);
        listView = (ListView) findViewById(R.id.messages_view);

        //Location
        Context context;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        getCurrentLocation();

        //Dialog Flow integration
        final AIConfiguration config = new AIConfiguration("29b55d38acc040de9808378b06429cf9",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);

        aiService.setListener(this);

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED) {

            makeRequest();

        }

        //Dialogflow End

        //Text to Speech
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        //Text to Speech end

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.message_right);
        listView.setAdapter(chatArrayAdapter);

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        //listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

        //voiceToText();
        //chatText();
        String name = "isara";

        chatArrayAdapter.add(new message(name,"Welcome to UtiliQue !",0,true));
        t1.speak("Welcome to UtiliQue !", TextToSpeech.QUEUE_FLUSH, null);

        voice_bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainChatActivity.this, ImageReco.class);
                startActivity(intent);
                getCurrentLocation();
            }
        });

        backbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backintent = new Intent(MainChatActivity.this, MainActivity.class);
                startActivity(backintent);
                getCurrentLocation();
            }
        });



    }

    //Text to Speech
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
    //Text to Speech end

    //Record permissions
    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {


                } else {

                }
                return;
            }
        }
    }

    //Record permissions end

    //Voice
    public void voiceBtn(View view){
        aiService.startListening();
    }

    @Override
    public void onResult(AIResponse result) {
        Log.d("result",result.toString());
        Result result1 = result.getResult();

        final String speech = result1.getFulfillment().getSpeech();

        //dialog.setText("Query "+result1.getResolvedQuery()+" action "+result1.getAction() + " Speech " + speech);

        String username = "isara";

        chatArrayAdapter.add(new message(username,result1.getResolvedQuery(),0,false));

        //chatArrayAdapter.add(new message(username,result1.getAction(),true));

        chatArrayAdapter.add(new message(username,speech,0,true));

        if (!side) side = true;
        else side = false;

        //Text to Speech

        String toSpeak = speech;

        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

        //Declare data types to use in Service request

        switch (result1.getResolvedQuery()) {
            case "need help":
                chatArrayAdapter.add(new message(username, "What type of help you need ?", 0, true));
                t1.speak("What type of help you need ?", TextToSpeech.QUEUE_FLUSH, null);

                break;
            case "go to home": {

                Intent intent = new Intent(MainChatActivity.this, MainActivity.class);
                Toast.makeText(this, "You are in the Home view !", Toast.LENGTH_SHORT).show();
                startActivity(intent);

                break;
            }
            case "go to feedback": {

                Intent intent = new Intent(MainChatActivity.this, Feedbacks.class);
                Toast.makeText(this, "You are in the Feedback view !", Toast.LENGTH_SHORT).show();
                startActivity(intent);

                break;
            }
            case "go to service request": {

                Intent intent = new Intent(MainChatActivity.this, ServiceRequestActivity.class);
                Toast.makeText(this, "You are in the Feedback view !", Toast.LENGTH_SHORT).show();
                startActivity(intent);

                break;
            }
            case "get location": {
                getCurrentLocation();
                Toast.makeText(this, "Lon"+ longitude, Toast.LENGTH_SHORT).show();
            }
            default:
                //Toast.makeText(this, "looping", Toast.LENGTH_SHORT).show();
                break;
        }

        //get from action
        //Set list of Categories
        switch (result1.getAction()) {
            case "newticket":
                chatArrayAdapter.add(new message(username, "Please choose the Type of the Service you need from the list", 1, true));
                break;
            case "technician":
                chatArrayAdapter.add(new message(username, "Please choose from the list", 2, true));
                break;
            case "vehicle":
                chatArrayAdapter.add(new message(username, "Please choose from the list", 3, true));
                break;
            case "it":
                chatArrayAdapter.add(new message(username, "Please choose from the list", 4, true));
                break;
            case "professional":
                chatArrayAdapter.add(new message(username, "Please choose from the list", 5, true));
                break;
            case "personalized":
                chatArrayAdapter.add(new message(username, "Please choose from the list", 6, true));
                break;
            case "beauty":
                chatArrayAdapter.add(new message(username, "Please choose from the list", 7, true));
                break;
            case "home":
                chatArrayAdapter.add(new message(username, "Please choose from the list", 8, true));
                break;
            default:
                //Toast.makeText(this, "click voice btn again", Toast.LENGTH_SHORT).show();
                break;
        }

        //Subcategory selection
        switch (result1.getResolvedQuery()) {
            case "AC repair":
                category = "Technician";
                subCategory = "AC Repairs";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "CCTV":
                category = "Technician";
                subCategory = "CCTV";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "electricians":
                category = "Technician";
                subCategory = "Electricians";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "electronic repairs":
                category = "Technician";
                subCategory = "Electronic Repairs";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "aluminium and glass":
                category = "Technician";
                subCategory = "Aluminium and Glass";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "machinery":
                category = "Technician";
                subCategory = "Machinery";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "odd jobs":
                category = "Technician";
                subCategory = "Odd jobs";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "pest controllers":
                category = "Technician";
                subCategory = "Pest Controllers";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "plumbing":
                category = "Technician";
                subCategory = "Plumbing";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "wood works":
                category = "Technician";
                subCategory = "Wood Works";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "auto mechanics":
                category = "Vehicle";
                subCategory = "Auto Mechanics";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "drivers":
                category = "Vehicle";
                subCategory = "Drivers";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "computer repairs":
                category = "IT";
                subCategory = "Computer Repairs";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "phone repairs":
                category = "IT";
                subCategory = "Phone Repairs";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "Web Mobile and Software":
                category = "IT";
                subCategory = "Web, Mobile and Software";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "accountancy":
                category = "Professional";
                subCategory = "Accountancy";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "arts and crafts":
                category = "Professional";
                subCategory = "Arts & Crafts";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "IT consultancy":
                category = "Professional";
                subCategory = "IT Consultancy";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "insurance agents":
                category = "Professional";
                subCategory = "Insurance agents";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "legal advices":
                category = "Professional";
                subCategory = "Legal Advices";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "loan brokers":
                category = "Professional";
                subCategory = "Loan Brokers";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "modeling":
                category = "Professional";
                subCategory = "Modeling";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "security":
                category = "Professional";
                subCategory = "Security";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "travel agents":
                category = "Professional";
                subCategory = "Travel Agents";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "caretaker":
                category = "Personalized";
                subCategory = "Caretaker";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "fitness training":
                category = "Personalized";
                subCategory = "Fitness Training";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "home nurse":
                category = "Personalized";
                subCategory = "Home Nurse";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "housemaids":
                category = "Personalized";
                subCategory = "Housemaids";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "sports":
                category = "Personalized";
                subCategory = "Sports";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "advertising and promotions":
                category = "Beauty";
                subCategory = "Advertising & Promotions";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "band dj and dancing":
                category = "Beauty";
                subCategory = "Band. Dj and Dancing";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "beauty salons":
                category = "Beauty";
                subCategory = "Beauty Salons";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "catering and Food":
                category = "Beauty";
                subCategory = "Catering &amp; Food";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "event planners":
                category = "Beauty";
                subCategory = "Fitness Training";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "flowers and decorations":
                category = "Beauty";
                subCategory = "Flowers and Decos";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "health and beauty":
                category = "Beauty";
                subCategory = "Health and Beauty";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "photography and videography":
                category = "Beauty";
                subCategory = "Photography &amp; Videography";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "cleaners":
                category = "Home";
                subCategory = "Cleaners";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "house painting":
                category = "Home";
                subCategory = "House Painting";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "interior designers":
                category = "Home";
                subCategory = "Interior Designers";
                Toast.makeText(this, category + " >> "+ subCategory, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }

        switch (result1.getResolvedQuery()) {
            case "low":
                priority = "Low";
                Toast.makeText(this, "priority :"+ priority + category + subCategory, Toast.LENGTH_SHORT).show();
                break;
            case "medium":
                priority = "Medium";
                Toast.makeText(this, "priority :"+ priority, Toast.LENGTH_SHORT).show();
                break;
            case "high":
                priority = "High";
                Toast.makeText(this, "priority :"+ priority, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        //Ask about the description
        if(speech.equals("You have selected the subcategory for new ticket !")){
            chatArrayAdapter.add(new message(username, "Please choose Priority of the service from the list and tell breif description about the service you need ? ", 9, true));
        }else if(speech.equals("Your description has recorded to our Data base thank you ! can we create the service request ? Please check the details and confirm!")){
            description = result1.getResolvedQuery();
            Toast.makeText(this, "Des " + description + category + subCategory, Toast.LENGTH_SHORT).show();

            getCurrentLocation();

            chatArrayAdapter.add(new message(username, "Service Category : " + category + "\n" +
                    "Service Sub Category : " + subCategory + "\n" +
                    "Service Priority : " + priority + "\n" +
                    "longitude : " + longitude + "\n" +
                    "latitude : " + latitude + "\n" +
                    "Service Description : " + description , 0, true));


        }else if(speech.equals("We created the new service request ! Do you need further assistance ?")){

            Toast.makeText(this, "Add lines for database", Toast.LENGTH_SHORT).show();

            // service request save data in DB
            addServiceData(username, category, subCategory,priority,description,longitude,latitude);

        }
        else if(speech.equals("Please wait till all the records generated")){

                getServices(username);

        }else{
            //
        }


    }

    private void requestPermissions(String[] strings) {
    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }


    public void addServiceData(String username,String category, String subCategory, String priority, String description, String longitude, String latitude) {

       mDatabase = FirebaseDatabase.getInstance().getReference().child("service");
       mDatabase.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.exists()){
                   maxid =(snapshot.getChildrenCount());
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

       service se = new service(username,category,subCategory,priority,description,longitude,latitude);

       mDatabase.child(String.valueOf(maxid + 1)).setValue(se);

    }

    public void getCurrentLocation(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null){
                            Double lat = location.getLatitude();
                            Double lon = location.getLongitude();

                            longitude = String.valueOf(lon);

                            latitude = String.valueOf(lat);


                        }
                    }
                });
            }else{

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
                Toast.makeText(this, "No GPS Locations", Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void getServices(String name){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("service");

        reference.orderByChild("username").equalTo(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                        
                        category = datas.child("category").getValue().toString();
                        subCategory = datas.child("subCategory").getValue().toString();
                        description = datas.child("description").getValue().toString();
                        priority = datas.child("priority").getValue().toString();

                        chatArrayAdapter.add(new message(name, "Service Category : " + category + "\n" +
                            "Service Sub Category : " + subCategory + "\n" +
                            "Service Priority : " + priority + "\n" +
                            "Service Description : " + description , 0, true));

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("dd", "Failed to read value.", databaseError.toException());
            }
        });
    }






}
