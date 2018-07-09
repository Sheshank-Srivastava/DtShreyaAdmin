package com.dietitianshreya.eatrightdietadmin.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    Intent intent;
    String TAG="MY TAG";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        Log.d("Message",remoteMessage.getMessageId());
        Log.d("Reminder","Notification recieved");


        if (android.os.Build.VERSION.SDK_INT >= 26) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, importance);
            mNotificationManager.createNotificationChannel(mChannel);

        }
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            try {
                JSONObject data = new JSONObject(remoteMessage.getData());

                    MyNotificationManager2.getInstance(getApplicationContext()).displayNotification(data.getString("title"),data.getString("data"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getTitle());
        }

        /*
        * If the device is having android oreo we will create a notification channel
        * */


        /*
        * Displaying a notification locally
        */
//        MyNotificationManager.getInstance(getApplicationContext()).displayNotification(intent.getExtras().getString("gcm.notification.title"),intent.getExtras().getString("gcm.notification.body") );
//        Log.d("My tag",intent.getExtras().toString());



        /*
        * If the device is having android oreo we will create a notification channel
        * */

     /*  if (android.os.Build.VERSION.SDK_INT >= 26) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, importance);
            mNotificationManager.createNotificationChannel(mChannel);

        }
*/
        /*
        * Displaying a notification locally
        */
       /* MyNotificationManager.getInstance(getApplicationContext()).displayNotification(intent.getExtras().getString("gcm.notification.title"),intent.getExtras().getString("gcm.notification.body") );
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        */
    }




}
