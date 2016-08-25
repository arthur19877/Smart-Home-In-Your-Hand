package com.example.arthur.smarthomeinyourhands;

/**
 * Created by Raz on 08/01/2016.
 */
public class UserDetails {
    private static String name;
    private static String password;

    public static synchronized String[] getNameAndPass(){
        String[] namePass = {name,password};
        return namePass;
    }

    public static synchronized void setNameAndPass(String name1, String password1){
        name = name1;
        password = password1;
    }
}
