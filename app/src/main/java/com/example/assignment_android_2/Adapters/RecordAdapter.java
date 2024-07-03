package com.example.assignment_android_2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_android_2.Data.Record;
import com.example.assignment_android_2.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder>{

    private final ArrayList<Record> records_list;

    public RecordAdapter(ArrayList<Record> records_list){
        this.records_list = records_list;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        Record record = getItem(position);

        // Bind the record data to the views
        holder.item_date.setText(record.getDate());
        holder.item_time.setText(record.getTime());
        holder.item_points.setText(String.valueOf(record.getPoints()));
        // Set background color based on position
        if (position % 2 == 0) {
            int colorEven = ContextCompat.getColor(holder.itemView.getContext(), R.color.blue_grey_100);
            holder.item_container.setBackgroundColor(colorEven);
        } else {
            int colorOdd = ContextCompat.getColor(holder.itemView.getContext(), R.color.blue_grey_50);
            holder.item_container.setBackgroundColor(colorOdd);
        }
    }

    @Override
    public int getItemCount() {
        return records_list.size();
    }

    private Record getItem(int position) {
        return records_list.get(position);
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayoutCompat item_container;
        private final TextView item_date;
        private final TextView item_time;
        private final TextView item_points;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            item_container = itemView.findViewById(R.id.item_container);
            item_date = itemView.findViewById(R.id.item_date);
            item_time = itemView.findViewById(R.id.item_time);
            item_points = itemView.findViewById(R.id.item_points);
        }
    }
}
