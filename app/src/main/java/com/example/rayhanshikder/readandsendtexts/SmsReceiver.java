package com.example.rayhanshikder.readandsendtexts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;


public class SmsReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBroadcastReceiver";
    private static final String api_user_name = "user123456";
    private static final String api_user_password = "123!@#";
    private static String fromNumber;
    private static String receivedMessage;

    public static void sendSms(Context context, String number, String message)
    {
        try{
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(fromNumber, null, message, null, null);
            Toast.makeText(context,
                    "SMS successfully sent!",
                    Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Toast.makeText(context,
                    "SMS faild, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i(TAG, "Intent recieved: " + intent.getAction());
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                if (messages.length > -1) {
                    Toast.makeText(context, "Message recieved: " + messages[0].getMessageBody() + " from " + messages[0].getDisplayOriginatingAddress(), Toast.LENGTH_SHORT).show();
                    fromNumber = new String(messages[0].getOriginatingAddress());
                    receivedMessage = new String(messages[0].getMessageBody()); // this is the message body
                    String[] parts = receivedMessage.split(" ");
                    if (parts.length == 2 && parts[0].equals("GED")) {
                        String code = new String(parts[1]);
                        try{
                            JSONObject jsonParams = new JSONObject();
                            jsonParams.put("product_code", code);
                            jsonParams.put("api_user",api_user_name);
                            jsonParams.put("api_password",api_user_password);
                            HttpUtils.post("dw/", jsonParams, new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    // If the response is JSONObject instead of expected JSONArray
                                    Log.d("response", "---------------- this is response : " + response);
                                    try {
                                        JSONObject serverResp = new JSONObject(response.toString());
                                        if(response.get("status").toString().equals("1")){
                                            Log.d("Expiry Date to send",response.get("expire_date").toString());
                                            sendSms(context, fromNumber, response.get("expire_date").toString());
                                        }
                                        if(response.get("status").toString().equals("-1")){
                                            Log.d("Expiry Date to send","-1 response, invalid code");
                                            sendSms(context, fromNumber, "Invalid Code! Please use the correct one.");
                                        }

                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        catch (Exception ex)
                        {
                            Log.e("error",ex.toString());
                        }



                    }
                }
            }
        }
    }
}