package com.digitalhomeland.storemanager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.digitalhomeland.storemanager.models.Notif;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prthma on 17-05-2017.
 */

public class NotifListAdapter extends BaseAdapter implements ListAdapter {
    private List<Notif> notificationList  = new ArrayList<>();
    private Context mContext;
    private Context context;

    public NotifListAdapter(Context mContext , List<Notif> notifList, Context context) {
        this.mContext = mContext;
        this.notificationList = notifList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notificationList.get(position).hashCode(); //fix it later
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view == null) {
            view = inflater.inflate(R.layout.view_list_item, null);

        }
        //Handle TextView and display string from your list
        TextView dateItemText = (TextView) view.findViewById(R.id.date_view);
        dateItemText.setText(notificationList.get(position).getDate() + " | ");
        TextView titleItemText = (TextView) view.findViewById(R.id.title_view);
        titleItemText.setText(notificationList.get(position).getTitle());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Log.d("myTag", "notiflistadapter : " +notificationList.get(position).getDate() );
                Intent i = new Intent( context , NotificationView.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("date", notificationList.get(position).getDate() );
                i.putExtra("title",notificationList.get(position).getTitle() );
                i.putExtra("subject",notificationList.get(position).getSubject() );
                mContext.startActivity(i);
            }
        });
        return view;
    }
}
