package com.example.arsalankhan.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.arsalankhan.broadcastreceiverdemo.Database.databaseAdapter;

public class MyReceiver extends BroadcastReceiver {

    public static final String INTENT_FILTER="my.own.intentFilter";
    @Override
    public void onReceive(Context context, Intent intent) {

        String status=intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if(status.equals(TelephonyManager.EXTRA_STATE_RINGING)){

            String number=intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            databaseAdapter database=new databaseAdapter(context);
            long id=database.insertNumber(number);

            if(id!=-1){
                Toast.makeText(context, "Number inserted into database", Toast.LENGTH_SHORT).show();
                Intent myintent=new Intent(INTENT_FILTER);
                context.sendBroadcast(myintent);
            }
        }
    }
}
