package com.example.arthur.smarthomeinyourhands;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Raz on 23/01/2016.
 */
public class GetRaspberryPiIP {

    private void refereshArp(Context ctx){
        WifiManager wm = (WifiManager) ctx.getSystemService(ctx.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        String parts[] = ip.replace(".","r").split("r");
        String subnet = parts[0] + "." + parts[1] + "." + parts[2] + ".";
        Runtime rt = Runtime.getRuntime();
        for(int i=0; i<256; i++){
            try{
                rt.exec("ping -c1 " + subnet + i);
            }catch(IOException e){
                continue;
            }
        }

    }

    public String getIpFromArpCache(String mac, Context context) {
        refereshArp(context);
        if (mac == null)
            return null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4 && mac.toLowerCase().equals(splitted[3])) {
                    // Basic sanity check
                    String ip = splitted[0];
                    return ip;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
