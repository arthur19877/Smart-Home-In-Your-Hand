package com.example.arthur.smarthomeinyourhands;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewRoom extends AppCompatActivity {
    EditText etRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_room);
        etRoom = (EditText)findViewById(R.id.tRoomName);
    }

    public void rEnterClick(View v)
    {
        String roomName = etRoom.getText().toString();
        Intent intent = new Intent();
        if(TextUtils.isEmpty(roomName)){
            Toast.makeText(getBaseContext(),"Please enter room name",Toast.LENGTH_LONG).show();
            return;
        }
        if(UserDbHelper.getInstance(this).addRoom(roomName,UserDetails.getNameAndPass()[0])== true) {
            Toast.makeText(getBaseContext(), "Room added", Toast.LENGTH_LONG).show();
            intent.putExtra("roomName", new String(roomName));
            setResult(60,intent);
            finish();
        }
        else
            Toast.makeText(getBaseContext(), "Room exist", Toast.LENGTH_LONG).show();
    }
    public void rBackClick(View v)
    {
        setResult(RESULT_CANCELED);
        finish();
    }
}
