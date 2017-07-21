package com.example.arsalankhan.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.arsalankhan.broadcastreceiverdemo.Database.databaseAdapter;

import java.lang.reflect.Method;

public class MyReceiver extends BroadcastReceiver {

    public static final String INTENT_FILTER = "my.own.intentFilter";


    @Override
    public void onReceive(Context context, Intent intent) {

        String status = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (status.equals(TelephonyManager.EXTRA_STATE_RINGING)) {

            String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            // for Auto Rejecting Call
            AutoRejectCall(context, number,intent);

            databaseAdapter database = new databaseAdapter(context);
            long id = database.insertNumber(number);

            if (id != -1) {
                Toast.makeText(context, "Number inserted into database", Toast.LENGTH_SHORT).show();
                Intent myintent = new Intent(INTENT_FILTER);
                context.sendBroadcast(myintent);
            }


        }
    }

    private void AutoRejectCall(Context context, String number,Intent intent) {
        try {

            String serviceManagerName = "android.os.ServiceManager";
            String serviceManagerNativeName = "android.os.ServiceManagerNative";
            String telephonyName = "com.android.internal.telephony.ITelephony";
            Class<?> telephonyClass;
            Class<?> telephonyStubClass;
            Class<?> serviceManagerClass;
            Class<?> serviceManagerNativeClass;
            Method telephonyEndCall;
            Object telephonyObject;
            Object serviceManagerObject;
            telephonyClass = Class.forName(telephonyName);
            telephonyStubClass = telephonyClass.getClasses()[0];
            serviceManagerClass = Class.forName(serviceManagerName);
            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
            Method getService = // getDefaults[29];
                    serviceManagerClass.getMethod("getService", String.class);
            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
            Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            telephonyObject = serviceMethod.invoke(null, retbinder);
            telephonyEndCall = telephonyClass.getMethod("endCall");
            telephonyEndCall.invoke(telephonyObject);

            //Sending Auto sms
            SendAutoSms(number);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void SendAutoSms(String number) {

        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(number,null,"I am Busy",null,null);
    }
}
