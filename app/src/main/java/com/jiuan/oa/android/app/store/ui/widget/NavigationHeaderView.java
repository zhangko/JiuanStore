package com.jiuan.oa.android.app.store.ui.widget;

import com.jiuan.oa.android.app.store.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NavigationHeaderView extends LinearLayout {

    private TextView name;

    private TextView account;

    public NavigationHeaderView(Context context) {
        this(context, null);
    }

    public NavigationHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.header_view, this, true);
        name = (TextView) linearLayout.findViewById(R.id.name);
        account = (TextView) linearLayout.findViewById(R.id.account);
    }

    public void setContent(String name, String account) {
        this.name.setText(name);
        this.account.setText(account);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
