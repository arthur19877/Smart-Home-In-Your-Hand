package com.example.arthur.smarthomeinyourhands;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.provider.CalendarContract;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Objects;

public class Menu extends AppCompatActivity implements View.OnClickListener {
    Context CTX = this;
    int cheack = 1;

    private static final int REQUEST_CODE = 1234;
    private ListView wordsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        onCreateRooms();
    }

    private void onCreateRooms()
    {
        try {
            UserDbHelper db = new UserDbHelper(CTX);
            Cursor CR = db.roomList(db);
            CR.moveToFirst();
            RoomDetails.setRoomName(CR.getString(0));
            do {
                createRoom(CR.getString(0));
            } while (CR.moveToNext());
        }catch (Exception e){}

        onCreateButtons();
    }
    private void onCreateButtons()
    {
        LinearLayout ll = (LinearLayout) findViewById(R.id.blLayout);
        ll.removeAllViews();
        LinearLayout ll2 = (LinearLayout) findViewById(R.id.blLayout2);
        ll2.removeAllViews();
        cheack =1;

        try {
            UserDbHelper db = new UserDbHelper(CTX);
            Cursor CR = db.buttonList(db);
            CR.moveToFirst();
            do {
                createButton(CR.getString(0), CR.getString(1));
            } while (CR.moveToNext());
        }catch (Exception e){}
    }

    public void onClickNewButton(View v) {
        if(RoomDetails.getRoomName() == null) {
            Toast.makeText(getBaseContext(), "Please Select Room", Toast.LENGTH_LONG).show();
            return;
        }
        Intent in = new Intent(Menu.this,NewButton.class);
        startActivityForResult(in, 132);
    }

    public void onClickNewRoom(View v) {
        Intent in = new Intent(Menu.this,NewRoom.class);
        startActivityForResult(in, 132);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == 50) {
            final String[] bNameAndPort;
            super.onActivityResult(requestCode, resultCode, data);
            bNameAndPort = data.getExtras().getStringArray("nameAndPort");
            createButton(bNameAndPort[0], bNameAndPort[1]);
        }
        else if(resultCode == 60)
        {
            final String roomName;
            super.onActivityResult(requestCode, resultCode, data);
            roomName = data.getExtras().getString("roomName");
            createRoom(roomName);
            UserDbHelper ud =new UserDbHelper(CTX);
            if(ud.roomListNumber(ud) == 5)
                ((LinearLayout)findViewById(R.id.blLayoutRooms)).removeView(findViewById(R.id.bNewRoom));
        }

        /**
         * Handle the results from the voice recognition activity.
         */

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            buttonSpeach(matches);
        }
    }

    private void buttonSpeach(ArrayList<String> buttons)
    {
        LinearLayout ll = (LinearLayout)findViewById(R.id.blLayout);
        LinearLayout ll2 = (LinearLayout)findViewById(R.id.blLayout2);

        for(int i =0;i<ll.getChildCount();i++)
        {
           if(buttons.contains(((ToggleButton) ll.getChildAt(i)).getText().toString()))
              raspebberyPi(true,(ToggleButton)ll.getChildAt(i));
        }
        for(int i =0;i<ll2.getChildCount();i++)
        {
            if(buttons.contains(((Button)ll2.getChildAt(i)).getText()))
                raspebberyPi(true, (ToggleButton) ll2.getChildAt(i));
        }
    }

    private void createButton(String name, final String port)
    {
        final ToggleButton myButton = new ToggleButton(this);
        ImageSpan imageSpan = new ImageSpan(this, android.R.drawable.ic_menu_info_details);
        SpannableString content = new SpannableString(name);
        content.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        myButton.setText(content);
        myButton.setTextOn(content);
        myButton.setTextOff(content);
        myButton.setText(name);
        myButton.setTag(port);
        myButton.setHighlightColor(0xFF36F3F3);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp1.setMargins(70, 20, 0, 20);
        LinearLayout ll = (LinearLayout)findViewById(R.id.blLayout);
        LinearLayout ll2 = (LinearLayout)findViewById(R.id.blLayout2);
        //AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        if(cheack < 5) { cheack ++;
            ll.addView(myButton, lp1);
        }
        else if(cheack > 4 && cheack <9){ cheack ++;
            ll2.addView(myButton, lp1);
        }

        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                raspebberyPi(false,myButton);
            }

        });

    }

    private void raspebberyPi(boolean isSpeech,ToggleButton bClick) {
        if(isSpeech)
            bClick.setChecked(!bClick.isChecked());
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(SocketHandler.getSocket().getOutputStream())), true);
            out.println(bClick.getTag());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createRoom(final String name)
    {
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,3);
        final Button myButton = new Button(this);
        myButton.setText(name);

        if(name.equals(RoomDetails.getRoomName()))
            myButton.setBackgroundColor(0xff80cbc4);
        else
        myButton.setBackgroundColor(0xffe0e0e0);

        myButton.setLayoutParams(lp1);
        LinearLayout ll = (LinearLayout)findViewById(R.id.blLayoutRooms);
        ll.addView(myButton);


        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                RoomDetails.setRoomName(name);
                onCreateButtons();
                LinearLayout ll = (LinearLayout) findViewById(R.id.blLayoutRooms);
                int childcount = ll.getChildCount();
                for (int i = 0; i < childcount; i++) {
                    View v = ll.getChildAt(i);
                    v.setBackgroundColor(0xffe0e0e0);
                }
                myButton.setBackgroundColor(0xff80cbc4);


            }
        });

    }

    /**
     * Handle the action of the button being clicked
     */
    public void speakButtonClicked(View v)
    {
        startVoiceRecognitionActivity();
    }

    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
        startActivityForResult(intent, REQUEST_CODE);
    }



    @Override
    public void onClick(View view) {

    }
}


