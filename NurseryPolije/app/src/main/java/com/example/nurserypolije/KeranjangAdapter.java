package com.example.nurserypolije;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nurserypolije.config.restServer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.HolderData> {
    private List<ModelKeranjang> mItems;
    private Context context;
    private ArrayList<ModelKeranjang> modelKeranjang;

    KeranjangAdapter(ArrayList<ModelKeranjang> arrayList) {
        this.modelKeranjang = arrayList;
    }

    public KeranjangAdapter(Context context, List<ModelKeranjang> mItems) {
        this.mItems = mItems;
        this.context = context;
    }

    @NonNull
    @Override
    public KeranjangAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_keranjang, viewGroup, false);
        KeranjangAdapter.HolderData holderData = new KeranjangAdapter.HolderData((layout));
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull final KeranjangAdapter.HolderData holder, int position) {
        ModelKeranjang modelKeranjang = mItems.get(position);
        //klik untuk Intent dan parsing data ke detail event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(holder.itemView.getContext(), DetailBunga.class);
                detail.putExtra("ID_BUNGA", holder.id_bunga);
                holder.itemView.getContext().startActivity(detail);
            }
        });

        try {
            holder.id_bunga = modelKeranjang.getId_bunga();
            holder.jumlahBeli.setText(modelKeranjang.getJumlah());
            holder.namaBunga.setText(modelKeranjang.getNama_bunga());
            Picasso.get()
                    .load(restServer.URL_FOTO_BUNGA + modelKeranjang.getFoto_bunga())
                    .into(holder.foto);
            holder.url = modelKeranjang.getFoto_bunga();
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
        TextView namaBunga, totalHarga, jumlahBeli;
        String url, id_bunga;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.ivBunga);
            namaBunga = itemView.findViewById(R.id.tvNamaBunga);
            jumlahBeli = itemView.findViewById(R.id.tvJumlahBeli);
            totalHarga = itemView.findViewById(R.id.tvTotalHarga);
        }
    }
}

