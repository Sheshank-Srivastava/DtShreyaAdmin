package com.dietitianshreya.eatrightdietadmin.Utils;



import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.dietitianshreya.eatrightdietadmin.MainActivity;
import com.dietitianshreya.eatrightdietadmin.R;

import static android.content.Context.NOTIFICATION_SERVICE;



public class MyNotificationManager {

    private Context mCtx;
    private static MyNotificationManager mInstance;

    private MyNotificationManager(Context context) {
        mCtx = context;
    }

    public static synchronized MyNotificationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyNotificationManager(context);
        }
        return mInstance;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayNotification(String title, String body) {
        Log.d("title",title);
        NotificationCompat.Builder mBuilder;
        if(!title.equalsIgnoreCase("meal")) {
            Log.d("title","i am here");
            mBuilder =
                    new NotificationCompat.Builder(mCtx, Constants.CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                            .setContentTitle(title)
                            .setContentText(body);
        }
        else
        {
            Log.d("lol","here ");
            Intent switchIntent = new Intent(mCtx, SwitchButtonListener.class);
            PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(mCtx, 0,
                    switchIntent, 0);
             mBuilder =
                    new NotificationCompat.Builder(mCtx, Constants.CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                            .setContentTitle(title)
                            .setContentText(body)
                            .addAction(R.drawable.ic_tick, "TAKEN",pendingSwitchIntent) // #0
                            .addAction(R.drawable.ic_cross, "MISSED", pendingSwitchIntent);


        }


//        //Yes intent
//        Intent yesReceive = new Intent();
//        yesReceive.setAction(Constants.YES_ACTION);
//        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(mCtx, 0, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.addAction(R.drawable.ic_triangle, "Yes", pendingIntentYes);
//
////No intent
//        Intent noReceive = new Intent();
//        noReceive.setAction(Constants.NO_ACTION);
//        PendingIntent pendingIntentNo = PendingIntent.getBroadcast(mCtx, 0, noReceive, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.addAction(R.drawable.ic_triangle, "No", pendingIntentNo);

        /*
        *  Clicking on the notification will take us to this intent
        *  Right now we are using the MainActivity as this is the only activity we have in our application
        *  But for your project you can customize it as you want
        * */
        mBuilder.setAutoCancel(true);
        Intent resultIntent = new Intent(mCtx, MainActivity.class);

        /*
        *  Now we will create a pending intent
        *  The method getActivity is taking 4 parameters
        *  All paramters are describing themselves
        *  0 is the request code (the second parameter)
        *  We can detect this code in the activity that will open by this we can get
        *  Which notification opened the activity
        * */
        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        /*
        *  Setting the pending intent to notification builder
        * */

        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotifyMgr =
                (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);


        /*
        * The first parameter is the notification id
        * better don't give a literal here (right now we are giving a int literal)
        * because using this id we can modify it later
        * */
        if (mNotifyMgr != null) {
            mNotifyMgr.notify(1, mBuilder.build());
        }
    }

    public void send()
    {

    }

}