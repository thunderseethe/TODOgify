package com.thunderseethe.todogfy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotificationService extends Service {

    public NotificationManager manager;

    public NotificationService() {}

    @Override
    public void onCreate(){
        manager = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Todo todo = intent.getParcelableExtra("todo");

        Intent main_activity = new Intent(this, MainActivity.class);
        PendingIntent pending_main_activity = PendingIntent.getActivity(this, 0, main_activity, 0);

        Intent complete_intent = new Intent(this, this.getClass());
        complete_intent.setAction("complete");
        complete_intent.putExtra("todo", todo);
        PendingIntent pending_complete = PendingIntent.getService(this, 0, complete_intent, 0);

        Notification notification =
            new NotificationCompat.Builder(this)
                                .setContentTitle(todo.task)
                                .setContentIntent(pending_main_activity)
                                .setOngoing(false)
                                .setSmallIcon(R.drawable.receipt)
                                .addAction(R.drawable.check, "Complete", pending_complete)
                                .build();

        //startForeground(0, notification);
        manager.notify(0, notification);
        Log.d("NotificationService", "notification displayed");


        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
