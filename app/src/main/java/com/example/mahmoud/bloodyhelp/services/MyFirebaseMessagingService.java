package com.example.mahmoud.bloodyhelp.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.mahmoud.bloodyhelp.R;
import com.example.mahmoud.bloodyhelp.util.Utilities;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

/**
 * Created by Mahmoud on 29/06/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    NotificationManager manager;
    Notification myNotication;
    Uri notification;
    Ringtone r;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null&& Utilities.get_settings_notif_pref(getApplicationContext())) {
            Log.d("hjgv", "Message Notification Body: " + remoteMessage.getNotification().getBody());


            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Bloody Help!")
                            .setContentText(remoteMessage.getNotification().getBody());

            notification  = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            r = RingtoneManager.getRingtone(getApplicationContext(), notification);

            r.play();
            Random rand = new Random();


            int mNotificationId =rand.nextInt(50)+1;
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(mNotificationId, mBuilder.build());


        }    }
}
