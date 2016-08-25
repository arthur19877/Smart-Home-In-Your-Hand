package com.example.arthur.smarthomeinyourhands;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity
{
    TextView tv1, tv2;
    Context CTX = this;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        tv1 = (EditText) findViewById(R.id.tUserName);
        tv2 = (EditText) findViewById(R.id.tPassword);
    }
    public void clickNewUser(View v)
    {
        Intent in = new Intent(this, NewUser.class);
        startActivity(in);
    }
    public void sEnterClick(View v)
    {
        if(SocketHandler.getIsAvailable() == 0) {
            Toast.makeText(getBaseContext(),"Plaese Make Sure Your Device Is Online",Toast.LENGTH_LONG).show();
            return;
        }
        try {

            String name = tv1.getText().toString();
            String password = tv2.getText().toString();
            UserDbHelper db = new UserDbHelper(CTX);
            Cursor CR = db.checkUser(db);
            boolean login_status = false;
            String NAME = "";
            CR.moveToFirst();
        do {
            if(name.equals(CR.getString(0)) && password.equals(CR.getString(1))) {
                login_status = true;
                NAME = CR.getString(0);
                UserDetails.setNameAndPass(CR.getString(0),CR.getString(1));
                break;
            }
        }while(CR.moveToNext());
        if(login_status)
        {
            Toast.makeText(getBaseContext(), "Login Success", Toast.LENGTH_LONG).show();
            Intent in = new Intent(this, Menu.class);
            finish();
            startActivity(in);
        }
        else
            Toast.makeText(getBaseContext(), "Your User name or Password id wrong", Toast.LENGTH_LONG).show();
        }catch (Exception e){Toast.makeText(getBaseContext(),"Please Add First User",Toast.LENGTH_LONG).show();return;}
    }
    public void sExit(View v)
    {
        finish();
    }
}
