package com.example.cs4720.my_application;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;


public class BucketListAdapter extends RecyclerView.Adapter<BucketListAdapter.ViewHolder> {
    private static ClickListener clickListener;

    private List<BucketItem> bucketList;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public BucketListAdapter(List<BucketItem> bucketItems) {
        bucketList = bucketItems;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewTitle;
        public TextView textViewDate;
        public CheckBox checkBoxChecked;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (TextView) itemView.findViewById(R.id.title);
            textViewDate = (TextView) itemView.findViewById(R.id.date);
            checkBoxChecked = (CheckBox) itemView.findViewById(R.id.checked);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    @Override
    public BucketListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View bucketListView = inflater.inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(bucketListView);
    }


    /**
     * onBindViewHolder() method
     * handle the tapping of the item name for launching the edit item activity and tapping the checkbox
     **/
    @Override
    public void onBindViewHolder(final BucketListAdapter.ViewHolder viewHolder, int position) {
        final BucketItem bucketItem = bucketList.get(position);
        viewHolder.textViewTitle.setText(bucketItem.getTitle());
        viewHolder.textViewDate.setText(sdf.format(bucketItem.getDate()));
        viewHolder.checkBoxChecked.setChecked(bucketItem.isChecked());
        viewHolder.checkBoxChecked.setTag(bucketItem);

        viewHolder.checkBoxChecked.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                BucketItem b = (BucketItem) cb.getTag();
                b.setChecked(cb.isChecked());
                bucketItem.setChecked(cb.isChecked());
                Collections.sort(bucketList);
                notifyItemRangeChanged(0, bucketList.size());
                Log.i("bucketItem", bucketItem.getTitle());
            }
        });
    }

    /**
     * returns number of items in the list
     **/
    @Override
    public int getItemCount() {
        return bucketList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public void setOnItemClickListener(ClickListener clickListener) {
        BucketListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public BucketItem getBucketItemAtPos(int pos) {
        return bucketList.get(pos);
    }
}