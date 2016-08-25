package com.example.arthur.smarthomeinyourhands;

import java.net.Socket;

/**
 * Created by Raz on 08/01/2016.
 */
public class SocketHandler {
    private static Socket socket;
    private static int isAvailable = 0;

    public static synchronized Socket getSocket(){return socket;}
    public static synchronized int getIsAvailable(){return isAvailable;}
    public static synchronized void setIsAvailable(int is){SocketHandler.isAvailable = is;}
    public static synchronized void setSocket(Socket socket){
        SocketHandler.socket = socket;
    }
}
