package com.thunderseethe.todogfy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dead on 4/2/16.
 */
public class Todo implements Parcelable {
    public final String task;
    public final boolean complete;

    public Todo(String _task){
        task = _task;
        complete = false;
    }
    public Todo(String _task, boolean _complete){
        task = _task;
        complete = _complete;
    }

    protected Todo(Parcel in) {
        task = in.readString();
        complete = in.readInt() != 0;
    }

    public Todo complete(boolean _complete) {
        return new Todo(task, _complete);
    }
    public Todo task(String _task) {
        return new Todo(_task, complete);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(task);
        out.writeInt(complete ? 1 : 0);
    }

    public static final Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if(o instanceof Todo) {
            Todo t = (Todo) o;
            return t.complete == this.complete && t.task.equals(this.task);
        }
        return false;
    }
}
