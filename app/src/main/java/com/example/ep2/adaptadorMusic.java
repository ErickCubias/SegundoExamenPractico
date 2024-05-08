package com.example.ep2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adaptadorMusic extends RecyclerView.Adapter<adaptadorMusic.SongViewHolder>{

    Context context;
    private LayoutInflater mInflater;
    private List<cancion> songList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public adaptadorMusic(List<cancion> songList, Context context, OnItemClickListener listener) {
        this.mInflater=LayoutInflater.from(context);
        this.songList = songList;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= mInflater.inflate(R.layout.items, null);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        holder.binData(songList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                if (listener != null && clickedPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClick(clickedPosition);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return songList.size();
    }

    public  class SongViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public ImageView imageView;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.txtNombre);
            imageView = itemView.findViewById(R.id.image);
        }

        void binData (final  cancion item){
            titleTextView.setText(item.getTittle());
            imageView.setBackgroundResource(item.getImage());

        }
    }

}
