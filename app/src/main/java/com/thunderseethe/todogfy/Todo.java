package com.thunderseethe.todogfy;

/**
 * Created by dead on 4/2/16.
 */
public class Todo {
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

    public Todo complete(boolean _complete) {
        return new Todo(task, _complete);
    }
    public boolean getComplete(){return complete;}
    public Todo task(String _task) {
        return new Todo(_task, complete);
    }
}
