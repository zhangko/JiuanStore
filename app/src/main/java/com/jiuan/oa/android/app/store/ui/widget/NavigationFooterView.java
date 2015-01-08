package com.jiuan.oa.android.app.store.ui.widget;

import com.jiuan.oa.android.app.store.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NavigationFooterView extends LinearLayout implements View.OnClickListener {

    public interface OnClickSettingsListener {

        void onClickSettings();

    }

    private OnClickSettingsListener mOnClickSettingsListener;

    public NavigationFooterView(Context context) {
        this(context, null);
    }

    public NavigationFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.footer_view, this, true);
        TextView settings = (TextView) linearLayout.findViewById(R.id.settings);
        settings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.settings) {
            if (mOnClickSettingsListener != null) {
                mOnClickSettingsListener.onClickSettings();
            }
        }
    }

    public void setOnClickSettingsListener(OnClickSettingsListener onClickSettingsListener) {
        mOnClickSettingsListener = onClickSettingsListener;
    }
}
