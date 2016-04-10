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
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotificationService extends Service {

    public NotificationManager manager;

    public NotificationService() {}

    @Override
    public void onCreate(){
        manager = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
    }

    public void start(Todo todo){
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
    }

    public void complete(Todo todo){
        SQLiteDatabase db = new TodoDB(this).getWritableDatabase();
        ContentValues values = new ContentValues();
        if(todo.id != -1)
            values.put(TodoDB.TodoEntry.COLUMN_ID, todo.id);
        values.put(TodoDB.TodoEntry.COLUMN_TASK, todo.task);
        values.put(TodoDB.TodoEntry.COLUMN_COMPLETED, 1);
        values.put(TodoDB.TodoEntry.COLUMN_PRIORITY, todo.priority);


        db.beginTransaction();

        db.insertWithOnConflict(TodoDB.TodoEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Cursor c = db.rawQuery(
            "SELECT id, task, completed, MAX(priority) priority FROM todo WHERE completed != 1"
            , null);

        /*
        "SELECT *"+
        " FROM " + TodoDB.TodoEntry.TABLE_NAME +
        " WHERE " + TodoDB.TodoEntry.COLUMN_COMPLETED + " != 1 " +
        " AND " + TodoDB.TodoEntry.COLUMN_PRIORITY + " == MAX(" + TodoDB.TodoEntry.COLUMN_PRIORITY + ")"
        */

        db.setTransactionSuccessful();
        db.endTransaction();

        c.moveToFirst();
        int id = c.getInt(c.getColumnIndex(TodoDB.TodoEntry.COLUMN_ID));
        String task  = c.getString(c.getColumnIndex(TodoDB.TodoEntry.COLUMN_TASK));
        boolean complete = c.getInt(c.getColumnIndex(TodoDB.TodoEntry.COLUMN_COMPLETED)) != 0;
        int priority = c.getInt(c.getColumnIndex(TodoDB.TodoEntry.COLUMN_PRIORITY));
        c.close();

        start(new Todo(id, task, complete, priority));

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("NotificationService", "onStartCommand");
        if(intent == null) return START_STICKY;
        if(intent.getAction() == null) return START_STICKY;

        Log.d("NotificationService", String.format("action: %s", intent.getAction()));
        if(intent.getAction().equals("start")){
            start((Todo)intent.getParcelableExtra("todo"));
        }
        if(intent.getAction().equals("complete")){
            complete((Todo)intent.getParcelableExtra("todo"));
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
