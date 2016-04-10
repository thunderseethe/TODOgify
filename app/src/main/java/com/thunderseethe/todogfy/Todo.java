package com.thunderseethe.todogfy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dead on 4/2/16.
 */
public class Todo implements Parcelable, Cloneable, Comparable<Todo> {
    public final int id;
    public final String task;
    public final boolean complete;
    public final int priority;

    public Todo(String _task){
        this(-1, _task, false);
    }
    public Todo(String _task, boolean _complete) {
        this(-1, _task, _complete);
    }
    public Todo(String _task, boolean _complete, int _importance){
        this(-1, _task, _complete, _importance);
    }
    public Todo(int _id, String _task, boolean _complete) {
        this(_id, _task, _complete, 0);
    }
    protected Todo(Parcel in) {
        this(in.readInt(), in.readString(), in.readInt() != 0, in.readInt());
    }
    public Todo(int _id, String _task, boolean _complete, int _priority) {
        id = _id;
        task = _task;
        complete = _complete;
        priority = _priority;
    }


    public Todo id(int _id) {
        return new Todo(_id, task, complete, priority);
    }
    public Todo task(String _task) {
        return new Todo(id, _task, complete, priority);
    }
    public Todo complete(boolean _complete) {
        return new Todo(id, task, _complete, priority);
    }
    public Todo priority(int _priority){
        return new Todo(id, task, complete, _priority);
    }

    //public int getImportance(){return importance;}
    //public void setImportance(int i){importance = i;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(task);
        out.writeInt(complete ? 1 : 0);
        out.writeInt(priority);
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

    @Override
    public Todo clone() {
        return new Todo(id, task, complete, priority);
    }

    @Override
    public int compareTo(Todo t) {
        return t.priority - this.priority;
    }
}
