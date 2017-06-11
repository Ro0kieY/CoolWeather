package com.sample.coolweather;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static com.sample.coolweather.ChooseAreaFragment.LEVEL_CITY;
import static com.sample.coolweather.ChooseAreaFragment.LEVEL_PROVINCE;

/**
 * Created by Jia on 2017/6/11.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<String> mDataList;
    private static OnItemClickListener mOnItemClickListener;

    //define interface
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public static void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView locationName;

        public ViewHolder(View itemView) {
            super(itemView);
            locationName = (TextView)itemView.findViewById(R.id.location_item);
        }
    }

    public LocationAdapter(List<String> dataList){
        mDataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String location = mDataList.get(position);
        holder.locationName.setText(location);
        if (mOnItemClickListener != null){
            holder.locationName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.locationName, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
