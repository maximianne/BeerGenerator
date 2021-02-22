package com.example.homework2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class beerAdapter extends RecyclerView.Adapter<beerAdapter.ViewHolder> {
    private ArrayList<Beer> beers;
   // private ArrayList<Beer> selectedBeer;
    private Context context;

    public beerAdapter(ArrayList<Beer> beers){
        this.beers=beers;
     //   this.selectedBeer= new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //inflate the custom layout
        View villagerView = inflater.inflate(R.layout.item_beer, parent, false);
        //return a new ViewHolder
        ViewHolder viewHolder = new ViewHolder(villagerView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Beer beer = beers.get(position);

        holder.textView_BeerName.setText(beer.getBeerName());
        holder.textView_description.setText(beer.getBeerDescription());
        Picasso.get().load(beer.getImage_url()).into(holder.imageView_beerPic);

        if(beer.isFav()){
            Picasso.get().load(beer.getImageSelect_url()).into(holder.imageView_favorite);
        }
        else{
            Picasso.get().load(beer.getImageStatic_url()).into(holder.imageView_favorite);
        }

        holder.imageView_favorite.setOnClickListener(v -> {
           if(beer.isFav()){
               beer.setFav(false);
           }
           else{
               beer.setFav(true);
           }
           this.notifyItemChanged(position);
        });
    }
    @Override
    public int getItemCount() {
        return beers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView_BeerName;
        TextView textView_description;
        ImageView imageView_beerPic;
        ImageView imageView_favorite;

        public ViewHolder(View itemView){
            super(itemView);
            context = itemView.getContext();
            textView_BeerName=itemView.findViewById(R.id.textView_BeerName);
            textView_description=itemView.findViewById(R.id.textView_description);
            imageView_beerPic=itemView.findViewById(R.id.imageView_beerPic);
            imageView_favorite=itemView.findViewById(R.id.imageView_favorite);
            imageView_beerPic.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent (context, FourthActivity.class);
            intent.putExtra("beer", beers.get(getAdapterPosition()));
            context.startActivity(intent);
        }
    }
}
