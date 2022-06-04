package com.example.memehub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MemeAdapter extends RecyclerView.Adapter<MemeAdapter.memeholder> {

    private ArrayList<String> urls=new ArrayList<>();
    private ArrayList<String> title=new ArrayList<>();
    private LayoutInflater inflater;


    MemeAdapter(Context context){
        this.inflater=LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public memeholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.memeitem, parent, false);
        return new memeholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull memeholder holder, int position) {
        String temp=title.get(position);
        holder.description.setText(temp);
        Glide.with(holder.itemView.getContext()).load(urls.get(position)).into(holder.image);

    }
    public void refreshArr(ArrayList<String> t,ArrayList<String> u){
        title.addAll(t);
        urls.addAll(u);
        notifyDataSetChanged();
    }
    public void reset(){
        title.clear();
        urls.clear();
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public class memeholder extends RecyclerView.ViewHolder{
        private ProgressBar loading;
        private ImageView image;
        private TextView description;
        public memeholder(@NonNull View itemView) {
            super(itemView);
            loading=itemView.findViewById(R.id.load);
            loading.setVisibility(itemView.INVISIBLE);
            image=itemView.findViewById(R.id.memeview);
            description=itemView.findViewById(R.id.name);

        }
    }


}