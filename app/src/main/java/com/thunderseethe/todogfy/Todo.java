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
    private final int priority;

    public Todo(String _task){this(-1, _task, false,0);
    }
    public Todo(String _task, boolean _complete) {
        this(-1, _task, _complete,0);
    }

    public Todo(String _task, boolean _complete, int _priority){
        this(-1,_task,_complete,_priority);
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

    public Todo priority(String _task) { //function to find the importance of a todo given the number of "!"s at the end of the string
        if(!_task.contains("!"))
            return new Todo(id,task,complete,0); //case for if there are no !'s
        int sum=0;
        for(int i=_task.lastIndexOf("!");i>=_task.indexOf("!");i--){//if there are !'s, step backwards through the string until you reach a non-! character
            if(_task.charAt(i)=='!')
                sum++;
            else
                break; //breaking if there's a character between the first and last "!"
        }
        if(sum>5)
            sum=5;
        return new Todo(id,task,complete,sum);
    }

    public Todo editTask(String _task){
        String clone; //cloning the _task for editting purposes
        int end =_task.length()-1;
        for(int i=_task.lastIndexOf("!"); i>= 0;i--) {
            if(_task.charAt(i)!='!'){
                end=i;
                break;
            }
        }

        clone=_task.substring(0,end+1);
        return new Todo(id,clone,complete,priority);
    }
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
        public Todo[] newArray(int size) {return new Todo[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if(o instanceof Todo) {
            Todo t = (Todo) o;
            return t.complete == this.complete && t.task.equals(this.task)&&t.priority==this.priority;
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
