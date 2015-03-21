package com.vib15.vibrations.app;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vib15.vibrations.app.data.EventsContract;


/**
 * Created by Agarwal on 07-03-2015.
 */
public class SponsorAdapter extends CursorAdapter {
    public SponsorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }
    public static class ViewHolder {
        public final ImageView iconView;
        public final TextView titleView;
        public final TextView descriptionView;

        public ViewHolder(View view) {
            iconView = (ImageView) view.findViewById(R.id.list_icon);
            titleView = (TextView) view.findViewById(R.id.list_title);
            descriptionView = (TextView) view.findViewById(R.id.list_desc);

        }
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int layoutId = R.layout.sponsors_item;
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.descriptionView.setText(cursor.getString(cursor.getColumnIndex(EventsContract.SponsorEntry.COLUMN_TYPE)));
        viewHolder.titleView.setText(cursor.getString(cursor.getColumnIndex(EventsContract.SponsorEntry.COLUMN_NAME)));
        viewHolder.iconView.setImageResource(cursor.getInt(cursor.getColumnIndex(EventsContract.SponsorEntry.COLUMN_LOGO)));
    }
}
