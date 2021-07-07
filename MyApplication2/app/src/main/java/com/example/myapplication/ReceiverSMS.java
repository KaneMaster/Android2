package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;

public class ReceiverSMS extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) return;
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i=0; i<pdus.length; i++){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i], bundle.getString("format"));
                }
                else
                {
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                }
            }
            String smsFromPhone = "<<" + messages[0].getDisplayOriginatingAddress() + ">>";
            StringBuilder body = new StringBuilder();
            for (int i = 0; i < messages.length; i++){
                body.append(messages[i].getMessageBody());
            }
            String fullText = body.toString();
            Singleton_Data sd = Singleton_Data.create();
            TextView tv = sd.getAct().findViewById(R.id.textView);
            tv.append(smsFromPhone + "\n" + fullText + "\n\n");

    }
}
