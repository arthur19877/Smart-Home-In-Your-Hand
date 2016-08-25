package com.example.arthur.smarthomeinyourhands;

/**
 * Created by Raz on 08/01/2016.
 */
public class RoomDetails {
    private static String roomName;

    public static synchronized String getRoomName() {
        return roomName;
    }
    public static synchronized void setRoomName(String name1){
        roomName = name1;
    }
}
