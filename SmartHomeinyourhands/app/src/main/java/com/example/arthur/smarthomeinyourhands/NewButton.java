package com.example.arthur.smarthomeinyourhands;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewButton extends AppCompatActivity {
    EditText tv1, tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_button);
        tv1 = (EditText) findViewById(R.id.tButtonName);
        tv2 = (EditText) findViewById(R.id.tSwitchPort);
    }
    public void bEnterClick(View v)
    {
        String buttonName = tv1.getText().toString();
        String portNum = tv2.getText().toString();
        if(TextUtils.isEmpty(buttonName)||TextUtils.isEmpty(portNum)){
            Toast.makeText(getBaseContext(),"Please Enter Button Name And Port",Toast.LENGTH_LONG).show();return;
        }
        Intent intent = new Intent();
        if(buttonName == null || portNum == null){
            Toast.makeText(getBaseContext(),"Please Enter Button Name And Port",Toast.LENGTH_LONG).show();return;}
        if(UserDbHelper.getInstance(this).addButton(buttonName, portNum, UserDetails.getNameAndPass()[0], RoomDetails.getRoomName()) == true) {
            Toast.makeText(getBaseContext(), "Button added", Toast.LENGTH_LONG).show();
            intent.putExtra("nameAndPort", new String[]{buttonName,portNum});
            setResult(50,intent);
            finish();
        }
        else
            Toast.makeText(getBaseContext(), "Port busy", Toast.LENGTH_LONG).show();
    }
    public void bBackClick(View v)
    {
        setResult(RESULT_CANCELED);
        finish();
    }
}
