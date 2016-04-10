package com.thunderseethe.todogfy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationService extends Service {

    private NotificationManager manager;
    //private List<Todo> content;

    public NotificationService() {}

    @Override
    public void onCreate(){
        manager = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
    }

    public void start(List<Todo> todos){
        Todo todo = Collections.min(todos);

        Intent main_activity = new Intent(this, MainActivity.class);
        PendingIntent pending_main_activity = PendingIntent.getActivity(this, 0, main_activity, 0);

        Intent complete_intent = new Intent(this, this.getClass());
        complete_intent.setAction("complete");
        complete_intent.putExtra("todo", todo);
        complete_intent.putParcelableArrayListExtra("todos", new ArrayList<>(todos));
        PendingIntent pending_complete = PendingIntent.getService(this, 0, complete_intent, PendingIntent.FLAG_UPDATE_CURRENT);

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
    }

    public void complete(Todo todo, List<Todo> todos){
        SQLiteDatabase db = new TodoDB(this).getWritableDatabase();
        ContentValues values = new ContentValues();

        if(todo.id != -1)
            values.put(TodoDB.TodoEntry.COLUMN_ID, todo.id);
        values.put(TodoDB.TodoEntry.COLUMN_TASK, todo.task);
        values.put(TodoDB.TodoEntry.COLUMN_COMPLETED, 1);
        values.put(TodoDB.TodoEntry.COLUMN_PRIORITY, todo.priority);

        db.insertWithOnConflict(TodoDB.TodoEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);


        for(int i = 0; i < todos.size(); i += 1) {
            if(todo.id == todos.get(i).id){
                Log.d("NotificationService", todos.get(i).toString());
                todos.remove(i);
            }
        }

        if(todos.isEmpty()) {
            Log.d("NotificationService", "empty content");
            stopSelf();
            return;
        }

        start(todos);
    }

    public void stop() {
        stopSelf();
    }

    public void onDestroy() {
        manager.cancel(0);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("NotificationService", "onStartCommand");
        if(intent == null) return START_STICKY;
        if(intent.getAction() == null) return START_STICKY;

        Log.d("NotificationService", String.format("action: %s", intent.getAction()));
        if(intent.getAction().equals("start")){
            List<Todo> todos = intent.getParcelableArrayListExtra("todos");
            start(todos);
        }
        if(intent.getAction().equals("complete")){
            List<Todo> todos = intent.getParcelableArrayListExtra("todos");
            complete((Todo)intent.getParcelableExtra("todo"), todos);
        }
        if(intent.getAction().equals("stop")){
            stopSelf();
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
