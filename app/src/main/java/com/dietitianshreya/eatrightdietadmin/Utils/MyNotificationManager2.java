package com.dietitianshreya.eatrightdietadmin.Utils;

/**
 * Created by hp on 2/10/2018.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;


import com.dietitianshreya.eatrightdietadmin.MainActivity;
import com.dietitianshreya.eatrightdietadmin.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Belal on 12/8/2017.
 */

public class MyNotificationManager2 {

    private Context mCtx;
    private static MyNotificationManager2 mInstance;

    private MyNotificationManager2(Context context) {
        mCtx = context;
    }

    public static synchronized MyNotificationManager2 getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyNotificationManager2(context);
        }
        return mInstance;
    }

    public void displayNotification(String title, String body) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mCtx,Constants.CHANNEL_ID)
                        .setSmallIcon(R.drawable.dietitian)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.dietitian))
                        .setContentTitle(title)
                        .setContentText(body);


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

}