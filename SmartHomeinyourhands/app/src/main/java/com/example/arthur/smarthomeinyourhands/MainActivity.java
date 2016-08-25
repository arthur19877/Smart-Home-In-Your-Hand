package com.example.arthur.smarthomeinyourhands;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class MainActivity extends AppCompatActivity {
    Context context = this;
    Socket socket;
    String ip, port, response = "";
    EditText eIp, ePort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eIp = (EditText)findViewById(R.id.tIp);
        ePort = (EditText)findViewById(R.id.tPort);
        try {
            String[] ipPort = readFile();
            eIp.setText(ipPort[0]);
            ePort.setText(ipPort[1]);
        }catch (Exception e){e.printStackTrace();}
        GetRaspberryPiIP getIp = new GetRaspberryPiIP();
        eIp.setText(getIp.getIpFromArpCache("B8:27:EB:39:F3:BB", context));


    }



    public void clickConnect(View v)
    {
        if(TextUtils.isEmpty(eIp.getText()) || TextUtils.isEmpty(ePort.getText())||!eIp.getText().toString().matches("^[0-9.]+$")){
            Toast.makeText(getBaseContext(),"Please Enter Ip And Port",Toast.LENGTH_LONG).show();return;
        }
        writeToFile();
        if(eIp.getText().toString().contains(""))
            new Thread(new ClientThread()).start();
        else
        {
            Toast.makeText(getApplicationContext(), "Please Insert IP Adress", Toast.LENGTH_LONG).show();
            return;
        }
        Intent in = new Intent(MainActivity.this, SignIn.class);
        startActivity(in);
    }
    public void wExit(View v)
    {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())),
                    true);
            out.println("quit");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    class ClientThread implements Runnable {
        @Override
        public void run() {
            do {
                try {
                    if(SocketHandler.getIsAvailable() ==1)
                        return;
                    InetAddress serverAddr = InetAddress.getByName(eIp.getText().toString());
                    int port = Integer.parseInt(ePort.getText().toString());
                    socket = new Socket(serverAddr, port);
                    Thread.sleep(3000);

                    ByteArrayOutputStream byteArrayOutputStream =
                            new ByteArrayOutputStream(1024);
                    byte[] buffer = new byte[1024];

                    int bytesRead;
                    InputStream inputStream = socket.getInputStream();

                    if(inputStream.available() != 0) {
                        bytesRead = inputStream.read(buffer);
                        byteArrayOutputStream.write(buffer, 0, bytesRead);
                        response += byteArrayOutputStream.toString("UTF-8");
                    }


                    if (response.contains("Receieve"))
                        SocketHandler.setIsAvailable(1);


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } while (SocketHandler.getIsAvailable() == 0);
            SocketHandler.setSocket(socket);
        }
    }

        private String[] readFile()
        {

            File path = context.getFilesDir();
            File file = new File(path, "ipPort.txt");
            int length = (int) file.length();

            byte[] bytes = new byte[length];
            try {
                FileInputStream in = new FileInputStream(file);
                in.read(bytes);
                in.close();
            }catch (Exception e){e.printStackTrace();}


            String[] contents = new String(bytes).split(" ");
            return contents;
        }
        private void writeToFile()
        {
            File path = context.getFilesDir();
            File file = new File(path, "ipPort.txt");
            FileOutputStream stream = null;
            try {
                stream = new FileOutputStream(file);
                stream.write((eIp.getText() + " ").getBytes());
                stream.write(ePort.getText().toString().getBytes());
                stream.close();
            } catch (Exception e) {e.printStackTrace();}
        }




}
