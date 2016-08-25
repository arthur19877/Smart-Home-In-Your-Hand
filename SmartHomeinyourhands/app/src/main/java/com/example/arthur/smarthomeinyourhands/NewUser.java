package com.example.arthur.smarthomeinyourhands;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewUser extends AppCompatActivity
{
    EditText tv1, tv;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_new_user);
            tv1 = (EditText) findViewById(R.id.ntNewUser);
            tv = (EditText) findViewById(R.id.ntPassword);
    }
    public void rClickEnter(View v)
    {
        String userName = tv1.getText().toString();
        String password = tv.getText().toString();

        if(TextUtils.isEmpty(password) || TextUtils.isEmpty(userName)){
            Toast.makeText(getBaseContext(),"Please Enter User Name And Password",Toast.LENGTH_LONG).show();
            return;
        }
        if(UserDbHelper.getInstance(this).addUser(userName,password) == true) {
            Toast.makeText(getBaseContext(), "User Saved", Toast.LENGTH_LONG).show();
            setResult(RESULT_OK);
            finish();
        }
        else
            Toast.makeText(getBaseContext(), "User exist", Toast.LENGTH_LONG).show();
    }
    public void rBackClick(View v)
    {
        setResult(RESULT_CANCELED);
        finish();
    }
}
