package com.thunderseethe.todogfy;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//https://github.com/thunderseethe/TODOgify.git

public class TodoAdapter extends RecyclerView.Adapter<TodoVH> {
    public static final int ITEM = 0;
    public static final int FOOTER = 1;

    public final List<Todo> content;

    public TodoAdapter(List<Todo> _content){
        this.content = _content;
    }

    @Override
    public TodoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if(viewType == ITEM)
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        else {
            Log.d("BindFooter", "created");
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_todo_item, parent, false);
        }

        return new TodoVH(v, viewType);
    }

    public void bindItem(final TodoVH holder, final int position) {
        final TodoAdapter adapter = this;
        final Todo item = this.content.get(position);

        holder.text_view.setText(item.task);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("OnClickListener", String.format("%d %b", position, !item.complete));
                adapter.content.set(position, item.complete(!item.complete));
                adapter.notifyItemChanged(position);
            }
        });

        /*if(item.complete)
            holder.text_view.setPaintFlags(holder.text_view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        else
            holder.text_view.setPaintFlags(holder.text_view.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));*/
        ((TodoCardView)holder.root).strikethrough(item.complete);
    }

    public void bindFooter(final TodoVH holder, final List<Todo> content) {
        //Empty for now
        final TodoAdapter adapter = this;
        Log.d("BindFooter", "called");
        holder.edit_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String task = holder.edit_text.getText().toString();
                holder.edit_text.setText("");

                adapter.notifyItemInserted(adapter.content.size());
                adapter.content.add(new Todo(task, false));

                holder.edit_text.requestFocus();
                //holder.edit_text.setText("");
                return true;
            }
        });
        //holder.edit_text.requestFocus();
    }

    @Override
    public void onBindViewHolder(final TodoVH holder, final int position) {
        switch (holder.type) {
            case ITEM:
                bindItem(holder, position);
                break;
            case FOOTER:
                bindFooter(holder, this.content);
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
