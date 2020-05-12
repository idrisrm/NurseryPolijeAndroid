package com.example.nurserypolije;

import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nurserypolije.R;
import com.example.nurserypolije.ModelHome;
import com.squareup.picasso.Picasso;
import com.example.nurserypolije.config.restServer;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HolderData> {
    private List<ModelHome> mItems;
    private Context context;
    private ArrayList<ModelHome> modelHome;

    HomeAdapter(ArrayList<ModelHome> arrayList) {
        this.modelHome = arrayList;
    }

    public HomeAdapter(Context context, List<ModelHome> mItems) {
        this.mItems = mItems;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_bunga, viewGroup, false);
        HomeAdapter.HolderData holderData = new HomeAdapter.HolderData((layout));
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderData holder, int position) {
        ModelHome modelHome = mItems.get(position);
        //klik untuk Intent dan parsing data ke detail event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(holder.itemView.getContext(), LoginActivity.class);
                detail.putExtra("ID_BUNGA", holder.id_bunga);
                holder.itemView.getContext().startActivity(detail);
            }
        });

        try {
            holder.id_bunga = modelHome.getId_bunga();
            holder.harga.setText(modelHome.getHarga());
            holder.namaBunga.setText(modelHome.getNama_bunga());
            holder.deskripsi.setText(modelHome.getDeskripsi());
            Picasso.get()
                    .load(restServer.URL_FOTO_BUNGA + modelHome.getFoto_bunga())
                    .into(holder.foto);
            holder.url = modelHome.getFoto_bunga();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        ImageView foto;
        TextView namaBunga, harga, deskripsi;
        String url, id_bunga;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.ivBunga);
            namaBunga = itemView.findViewById(R.id.tvNamaBunga);
            harga = itemView.findViewById(R.id.tvHarga);
            deskripsi = itemView.findViewById(R.id.tvDeskripsi);
        }
    }
}
