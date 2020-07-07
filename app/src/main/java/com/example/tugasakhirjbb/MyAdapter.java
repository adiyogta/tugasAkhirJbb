package com.example.tugasakhirjbb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<barangdata> barangdatas;

    public MyAdapter(Context c , ArrayList<barangdata> p)
    {
        context = c;
        barangdatas = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
//        holder.fotobr.setImageResource((barangdatas.get(position).getImageURL()));
        holder.namabra.setText(barangdatas.get(position).getNamaB());
        holder.hargabra.setText(barangdatas.get(position).getHargaB());
        Glide.with(context)
                .load(barangdatas.get(position).getImageURL())
                .into(holder.fotobr);
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailBarang.class);
                intent.putExtra("Image", barangdatas.get(holder.getAdapterPosition()).getImageURL());
                intent.putExtra("Nama", barangdatas.get(holder.getAdapterPosition()).getNamaB());
                intent.putExtra("Ukuran", barangdatas.get(holder.getAdapterPosition()).getUkuranB());
                intent.putExtra("Kekurangan", barangdatas.get(holder.getAdapterPosition()).getKekuranganB());
                intent.putExtra("Lokasi", barangdatas.get(holder.getAdapterPosition()).getLokasiB());
                intent.putExtra("Harga", barangdatas.get(holder.getAdapterPosition()).getHargaB());
                intent.putExtra("Keterangan", barangdatas.get(holder.getAdapterPosition()).getKeteranganB());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return barangdatas.size();
    }

    public void filteredList(ArrayList<barangdata> filterList) {

        barangdatas = filterList;
        notifyDataSetChanged();

    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView namabra,ukuranbra,kekuranganbra,lokasibra,hargabra,keteranganbra;
        ImageView fotobr;
        CardView detail;
        public MyViewHolder(View itemView) {
            super(itemView);
            namabra = (TextView) itemView.findViewById(R.id.namabr);
            hargabra = (TextView) itemView.findViewById(R.id.hargabr);
            fotobr = (ImageView) itemView.findViewById(R.id.fotobr9);
            detail = (CardView) itemView.findViewById(R.id.cardv);

        }

    }
}
