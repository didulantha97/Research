package com.sliit.CDAP.smart_service_application.ChatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sliit.CDAP.smart_service_application.Models.message;
import com.sliit.CDAP.smart_service_application.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ChatArrayAdapter extends ArrayAdapter<message> {

    private TextView chatText, itemText;
    private List<message> chatMessageList = new ArrayList<message>();
    private Context context;
    private ListView list, secList;
    String[] listItem = {"Technicians",
            "Vehicles",
            "IT",
            "Professional",
            "Personalized Services",
            "Beauty and Events",
            "Home"};

    String[] listTechnicians = {"AC Repairs" ,
            "CCTV " ,
            "Electricians" ,
            "Electronic Repairs" ,
            "Aluminium and Glass" ,
            "Machinery" ,
            "Odd jobs" ,
            "Pest Controllers" ,
            "Plumbing" ,
            "Wood Works"};

    String[] listVehicle = {"Auto Mechanics" ,
            "Drivers"};

    String[] listIt = {"Computer Repairs" ,
            "Phone Repairs" ,
            "Web, Mobile and Software"};

    String[] listProfessional = {"Accountancy \n" +
            "Arts & Crafts" ,
            "IT Consultancy" ,
            "Insurance agents" ,
            "Legal Advices" ,
            "Loan Brokers" ,
            "Modeling" ,
            "Security" ,
            "Travel Agents"};

    String[] listPersonalized = {"Caretaker" ,
            "Fitness Training" ,
            "Home Nurse" ,
            "Housemaids" ,
            "Sports"};

    String[] listBeauty = {"Advertising & Promotions" ,
            "Band. Dj and Dancing",
            "Beauty Salons ",
            "Catering &amp; Food",
            "Event Planners ",
            "Flowers and Decos ",
            "Health and Beauty ",
            "Photography &amp; Videography "};

    String[] listHome = {"Cleaners" ,
            "House Painting" ,
            "Interior Designers"};

    String[] listPriority = {"Low" ,
            "Medium" ,
            "High"};



    public ChatArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(message object){
        chatMessageList.add(object);
        super.add(object);
    }


    public int getCount() {
        return this.chatMessageList.size();
    }

    public message getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        message chatMessageObj = getItem(position);


        View row = convertView;

        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (chatMessageObj.left) {

            //row = inflater.inflate(R.layout.message_left, parent, false);

            if(chatMessageObj.list == 1){

                row = inflater.inflate(R.layout.list_left, parent, false);

                list = (ListView) row.findViewById(R.id.typeService);

                //itemText=(TextView) row.findViewById(R.id.chatListItem);

                //listItem = getResources().getStringArray(R.array.array_technology);

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, android.R.id.text1, listItem);

                list.setAdapter(adapter);
                list.setDivider(new ColorDrawable(0xffeaeaea));


                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            // TODO Auto-generated method stub
                            String value = adapter.getItem(position);

                            //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

                            prefs.edit().putString("key", value).apply();
                            //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                        }
                });


            }else if(chatMessageObj.list == 2){
                row = inflater.inflate(R.layout.list_left, parent, false);

                list = (ListView) row.findViewById(R.id.typeService);

                //itemText=(TextView) row.findViewById(R.id.chatListItem);

                //listItem = getResources().getStringArray(R.array.array_technology);

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, android.R.id.text1, listTechnicians);

                list.setAdapter(adapter);
                list.setDivider(new ColorDrawable(0xffeaeaea));


                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        // TODO Auto-generated method stub
                        String value = adapter.getItem(position);

                        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

                        prefs.edit().putString("key", value).apply();
                        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else if(chatMessageObj.list == 3){
                row = inflater.inflate(R.layout.list_left, parent, false);

                list = (ListView) row.findViewById(R.id.typeService);

                //itemText=(TextView) row.findViewById(R.id.chatListItem);

                //listItem = getResources().getStringArray(R.array.array_technology);

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, android.R.id.text1, listVehicle);

                list.setAdapter(adapter);
                list.setDivider(new ColorDrawable(0xffeaeaea));


                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        // TODO Auto-generated method stub
                        String value = adapter.getItem(position);

                        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

                        prefs.edit().putString("key", value).apply();
                        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else if(chatMessageObj.list == 4){
                row = inflater.inflate(R.layout.list_left, parent, false);

                list = (ListView) row.findViewById(R.id.typeService);

                //itemText=(TextView) row.findViewById(R.id.chatListItem);

                //listItem = getResources().getStringArray(R.array.array_technology);

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, android.R.id.text1, listIt);

                list.setAdapter(adapter);
                list.setDivider(new ColorDrawable(0xffeaeaea));


                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        // TODO Auto-generated method stub
                        String value = adapter.getItem(position);

                        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

                        prefs.edit().putString("key", value).apply();
                        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else if(chatMessageObj.list == 5){
                row = inflater.inflate(R.layout.list_left, parent, false);

                list = (ListView) row.findViewById(R.id.typeService);

                //itemText=(TextView) row.findViewById(R.id.chatListItem);

                //listItem = getResources().getStringArray(R.array.array_technology);

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, android.R.id.text1, listProfessional);

                list.setAdapter(adapter);
                list.setDivider(new ColorDrawable(0xffeaeaea));


                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        // TODO Auto-generated method stub
                        String value = adapter.getItem(position);

                        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

                        prefs.edit().putString("key", value).apply();
                        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else if(chatMessageObj.list == 6){
                row = inflater.inflate(R.layout.list_left, parent, false);

                list = (ListView) row.findViewById(R.id.typeService);

                //itemText=(TextView) row.findViewById(R.id.chatListItem);

                //listItem = getResources().getStringArray(R.array.array_technology);

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, android.R.id.text1, listPersonalized);

                list.setAdapter(adapter);
                list.setDivider(new ColorDrawable(0xffeaeaea));


                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        // TODO Auto-generated method stub
                        String value = adapter.getItem(position);

                        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

                        prefs.edit().putString("key", value).apply();
                        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else if(chatMessageObj.list == 7){
                row = inflater.inflate(R.layout.list_left, parent, false);

                list = (ListView) row.findViewById(R.id.typeService);

                //itemText=(TextView) row.findViewById(R.id.chatListItem);

                //listItem = getResources().getStringArray(R.array.array_technology);

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, android.R.id.text1, listBeauty);

                list.setAdapter(adapter);
                list.setDivider(new ColorDrawable(0xffeaeaea));


                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        // TODO Auto-generated method stub
                        String value = adapter.getItem(position);

                        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

                        prefs.edit().putString("key", value).apply();
                        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else if(chatMessageObj.list == 8){
                row = inflater.inflate(R.layout.list_left, parent, false);

                list = (ListView) row.findViewById(R.id.typeService);

                //itemText=(TextView) row.findViewById(R.id.chatListItem);

                //listItem = getResources().getStringArray(R.array.array_technology);

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, android.R.id.text1, listHome);

                list.setAdapter(adapter);
                list.setDivider(new ColorDrawable(0xffeaeaea));


                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        // TODO Auto-generated method stub
                        String value = adapter.getItem(position);

                        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

                        prefs.edit().putString("key", value).apply();
                        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else if(chatMessageObj.list == 9){
                row = inflater.inflate(R.layout.list_left, parent, false);

                list = (ListView) row.findViewById(R.id.typeService);

                //itemText=(TextView) row.findViewById(R.id.chatListItem);

                //listItem = getResources().getStringArray(R.array.array_technology);

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, android.R.id.text1, listPriority);

                list.setAdapter(adapter);
                list.setDivider(new ColorDrawable(0xffeaeaea));


                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        // TODO Auto-generated method stub
                        String value = adapter.getItem(position);

                        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

                        prefs.edit().putString("key", value).apply();
                        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else{
                row = inflater.inflate(R.layout.message_left, parent, false);
            }

        }
        else{
            row = inflater.inflate(R.layout.message_right, parent, false);
        }

        chatText = (TextView) row.findViewById(R.id.message_body);
        chatText.setText(chatMessageObj.message);


        return row;
    }

}
