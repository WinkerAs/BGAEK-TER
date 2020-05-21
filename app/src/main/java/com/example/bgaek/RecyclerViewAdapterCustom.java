package com.example.bgaek;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapterCustom extends RecyclerView.Adapter<RecyclerViewAdapterCustom.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mAuthor = new ArrayList<>();
    private Context mContext;
    OnNoteListenner onNoteListenner;

    public RecyclerViewAdapterCustom(Context mContext, ArrayList<String> mAuthor, OnNoteListenner onNoteListenner) {
        this.mAuthor = mAuthor;
        this.mContext = mContext;
        this.onNoteListenner = onNoteListenner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view, onNoteListenner);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewItem.setText(mAuthor.get(position));
    }


    @Override
    public int getItemCount() {
        return mAuthor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewItem;
        OnNoteListenner onNoteListenner;
        public ViewHolder(@NonNull View itemView, OnNoteListenner onNoteListenner) {
            super(itemView);
            textViewItem = itemView.findViewById(R.id.textViewItem);
            this.onNoteListenner = onNoteListenner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListenner.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListenner{
        void onNoteClick(int postition);
    }
}
