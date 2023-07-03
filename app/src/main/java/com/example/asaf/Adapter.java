package com.example.asaf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private final RecycleViewIterface recycleViewIterface;
    Context context;
    ArrayList<DriveModel> DriveList;

    public Adapter(Context context, ArrayList<DriveModel> DriveList,RecycleViewIterface recycleViewIterface){
        this.context=context;
        this.DriveList=DriveList;
        this.recycleViewIterface=recycleViewIterface;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //rows design
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent,false);
        return new Adapter.MyViewHolder(view, recycleViewIterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        //
        holder.tvName.setText(DriveList.get(position).getName());
        holder.tvFrom.setText(DriveList.get(position).getFrom());
        holder.tvTo.setText(DriveList.get(position).getTo());
        //holder.image.setImageResource(DriveList.get(position).get());
    }

    @Override
    public int getItemCount() {
        //total items
        return DriveList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //grab the views

        ImageView image;
        TextView tvName, tvFrom, tvTo;
        public MyViewHolder(@NonNull View itemView,RecycleViewIterface recycleViewIterface) {
            super(itemView);

            image = itemView.findViewById(R.id.iv_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvFrom = itemView.findViewById(R.id.tv_from);
            tvTo = itemView.findViewById(R.id.tv_to);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recycleViewIterface != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            recycleViewIterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public void filterList(ArrayList<DriveModel> filteredList) {
        DriveList = filteredList;
        notifyDataSetChanged();
    }
}