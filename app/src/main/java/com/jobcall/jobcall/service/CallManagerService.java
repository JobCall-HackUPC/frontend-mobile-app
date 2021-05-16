package com.jobcall.jobcall.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.RemoteMessage;
import com.jobcall.jobcall.R;
import com.jobcall.jobcall.activity.CallActivity;
import com.jobcall.jobcall.activity.SignUpActivity;

import java.util.HashMap;
import java.util.Map;

public class CallManagerService extends Service {
    FirebaseFirestore db;
    DocumentReference docRef;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }
    MediaPlayer playerCustom;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        docRef = db.collection("user").document(intent.getStringExtra("email"));
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists() && snapshot.get("call_id") != null) {
                    String callId =  snapshot.getData().get("call_id").toString();
                    Log.w("TAG", "File updated!.", e);
                    sendNotification("SOMEONE IS CALLIG YOU! :O", callId);
                    Map<String,Object> updates = snapshot.getData();
                    updates.put("call_id", FieldValue.delete());
                    docRef.update(updates);

                    /// Mas tarde. Ahora generar Notification
                    //Intent call = new Intent(getApplicationContext(), CallActivity.class);
                    //call.putExtra("call_id", callId);
                    //startActivity(call);
                    //Log.d("TAG", "Current data: " + snapshot.getData());
                } else {
                    Log.d("TAG", "Current data: null");
                }
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }


    private void sendNotification(String messageBody, String callId) {
        Intent intent = new Intent(this, CallActivity.class);
        intent.putExtra("url", com.jobcall.jobcall.utils.Constants.CLIENT_BASE + callId);
        Log.d("WEB", "attacking to " + intent.getStringExtra("url"));
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP );
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
         MediaPlayer playerCustom;
        playerCustom = MediaPlayer.create(this, R.raw.tone);
        playerCustom.setLooping(true);
        String channelId = getString(R.string.default_notification_channel_id);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_stat_ic_notification)
                        .setContentTitle(getString(R.string.fcm_message))
                        .setContentText(messageBody)
                        .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        playerCustom.start();

    }

}