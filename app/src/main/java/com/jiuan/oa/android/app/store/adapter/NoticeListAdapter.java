package com.jiuan.oa.android.app.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import com.jiuan.oa.android.app.store.R;
import com.jiuan.oa.android.app.store.noticehttplibrary.NoticeResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhangKong on 2015/6/30.
 */
public class NoticeListAdapter extends ArrayAdapter {

    private LayoutInflater mInflater;
    private List<NoticeResponse> mfilelist;

    private final static int NOTICE_READED = 0;

    private final static int NOTICE_NOT_READ = 1;
//    private TextView titleText;
//    private TextView timeText;

    public NoticeListAdapter(Context context, int textViewResourceId,
                             List objects){
        super(context, textViewResourceId, objects);
        mInflater = LayoutInflater.from(context);
        mfilelist = objects;

    }

    public NoticeListAdapter(Context context, int textViewResourceId){
        super(context,textViewResourceId);
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return mfilelist.size();
    }

    public Object getItem(int position) {
        return mfilelist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(R.layout.layout_notice_relative,null);
      ViewHolder  holder = new ViewHolder();

        holder.titleText = (TextView)convertView.findViewById(R.id.title_text);
        holder.timeText = (TextView)convertView.findViewById(R.id.time_text);
        holder.contentText = (TextView)convertView.findViewById(R.id.content_text);


        convertView.setTag(holder);

        holder.titleText.setPadding(0,5,0,5);

        if(mfilelist.get(position).getTag() == NOTICE_READED ){
            holder.titleText.setText(mfilelist.get(position).getTitleName());
            holder.timeText.setText(getCurrentDate(mfilelist.get(position).getTimeStamp()));
            holder.contentText.setText("");
            holder.titleText.setTextColor(getContext().getResources().getColor(android.R.color.darker_gray));
            holder.timeText.setTextColor(getContext().getResources().getColor(android.R.color.darker_gray));
            holder.contentText.setTextColor(getContext().getResources().getColor(android.R.color.darker_gray));
        }else if(mfilelist.get(position).getTag() == NOTICE_NOT_READ){
            holder.titleText.setText(mfilelist.get(position).getTitleName());
            holder.timeText.setText(getCurrentDate(mfilelist.get(position).getTimeStamp()));
            holder.contentText.setText("");
            holder.titleText.setTextColor(getContext().getResources().getColor(android.R.color.black));
            holder.timeText.setTextColor(getContext().getResources().getColor(android.R.color.black));
            holder.contentText.setTextColor(getContext().getResources().getColor(android.R.color.black));

        }

        return  convertView;

    }

    private String getCurrentDate(String timestamp){
        String timestampstr = timestamp + "000";
        long time = Long.parseLong(timestampstr);
        Date date = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
        String timeTodate = sf.format(date);
        return timeTodate;
    }

    public void addItem(List<NoticeResponse> items) {
       // checkListNull();
        mfilelist.addAll(items);
        notifyDataSetChanged();
    }
    public void checkListNull() {
        if (mfilelist == null) {
           mfilelist = new ArrayList<NoticeResponse>();
        }
    }



    class ViewHolder {
        TextView titleText;
        TextView timeText;
        TextView contentText;
    }

}
