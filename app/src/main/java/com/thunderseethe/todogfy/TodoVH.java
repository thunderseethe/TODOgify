package com.thunderseethe.todogfy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by dead on 4/2/16.
 */
public class TodoVH extends RecyclerView.ViewHolder {

    public final int type;

    public TextView text_view;
    public EditText edit_text;

    public View root;

    public TodoVH(View itemView, int type) {
        super(itemView);
        this.type = type;
        this.root = itemView;
        if(type == TodoAdapter.ITEM)
            this.text_view = (TextView) itemView.findViewById(R.id.text_view);
        if(type == TodoAdapter.FOOTER)
            this.edit_text = (EditText) itemView.findViewById(R.id.edit_text);
    }
}
