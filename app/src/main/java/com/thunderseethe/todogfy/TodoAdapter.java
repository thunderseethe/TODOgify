package com.thunderseethe.todogfy;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

//https://github.com/thunderseethe/TODOgify.git

public class TodoAdapter extends RecyclerView.Adapter<TodoVH> {
    public static final int ITEM = 0;
    public static final int FOOTER = 1;

    public final List<Todo> content;
    private boolean editing = false;



    public TodoAdapter(List<Todo> _content){
        this.content = _content;
    }

    @Override
    public TodoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if(viewType == ITEM)
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        else
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_todo_item, parent, false);

        return new TodoVH(v, viewType);
    }

    public void bindItem(final TodoVH holder, final int position) {
        final TodoAdapter adapter = this;
        final Todo item = this.content.get(position);

        //Log.d("Priority", String.format("%d %s", item.priority, new String(new char[item.priority]).replace('\0', '!')));

        holder.text_view.setText(item.task);
        holder.priority_view.setText(new String(new char[item.priority]).replace('\0', '!'));
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.content.set(position, item.complete(!item.complete));
                adapter.notifyItemChanged(position);
            }
        });

        ((TodoCardView)holder.root).strikethrough(item.complete);
        if(item.complete) {
            holder.root.setBackgroundColor(0xFFE0E0E0);
            holder.text_view.setTextColor(0xFFAAAAAA);
            holder.priority_view.setTextColor(0xFFAAAAAA);
        } else {
            holder.root.setBackgroundColor(0xFFFFFFFF);
            holder.text_view.setTextColor(0xFF000000);
            holder.priority_view.setTextColor(0xFF30426B);
        }
    }

    private Todo _priority(int priority, String _task){
        char last_char = _task.charAt(_task.length() - 1);
        if(last_char != '!')
            return new Todo(_task, false, priority);
        else
            return _priority(priority + 1, _task.substring(0, _task.length() - 1));
    }
    public Todo priority(String _task) {
        return _priority(0, _task);
    }


    public void bindFooter(final TodoVH holder) {
        //Empty for now
        final TodoAdapter adapter = this;

        if(editing)
            holder.edit_text.requestFocus();

        holder.edit_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                final String task = holder.edit_text.getText().toString();

                adapter.content.add(priority(task.trim()));
                Collections.sort(adapter.content);
                adapter.notifyDataSetChanged();

                editing = true;
                holder.edit_text.setText("");

                return true;
            }
        });
    }

    @Override
    public void onBindViewHolder(final TodoVH holder, final int position) {
        switch (holder.type) {
            case ITEM:
                bindItem(holder, position);
                break;
            case FOOTER:
                bindFooter(holder);
                break;
        }


    }

    @Override
    public int getItemViewType(int position) {
        if(position >= this.content.size()) {
            return FOOTER;
        } else {
            return ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return this.content.size() + 1;
    }
}
