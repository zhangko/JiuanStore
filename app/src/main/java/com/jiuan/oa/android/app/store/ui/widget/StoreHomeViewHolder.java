package com.jiuan.oa.android.app.store.ui.widget;

import com.jiuan.oa.android.app.store.R;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class StoreHomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // each data item is just a string in this case
    public ImageView icon;

    public TextView name;

    public TextView version;

    public TextView state;

    public StoreHomeViewHolder(CardView v) {
        super(v);
        v.setOnClickListener(this);
        icon = (ImageView) v.findViewById(R.id.icon);
        name = (TextView) v.findViewById(R.id.name);
        version = (TextView) v.findViewById(R.id.version);
        state = (TextView) v.findViewById(R.id.state);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(getPosition());
        }
    }

    public interface onItemClickListener {

        void onItemClick(int position);
    }

    private onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
