package com.example.nurserypolije;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DikemasAdapter extends RecyclerView.Adapter<DikemasAdapter.HolderData> {
    private List<ModelDikemas> mItems;
    private Context context;
    private ArrayList<ModelDikemas> modelDikemas;

    DikemasAdapter(ArrayList<ModelDikemas> arrayList) {
        this.modelDikemas = arrayList;
    }

    public DikemasAdapter(Context context, List<ModelDikemas> mItems) {
        this.mItems = mItems;
        this.context = context;
    }

    @NonNull
    @Override
    public DikemasAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_dikemas, viewGroup, false);
        DikemasAdapter.HolderData holderData = new DikemasAdapter.HolderData((layout));
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull final DikemasAdapter.HolderData holder, int position) {
        ModelDikemas modelDikemas = mItems.get(position);
        //klik untuk Intent dan parsing data ke detail event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(holder.itemView.getContext(), DetailTagihan.class);
                detail.putExtra("ID_TRANSAKSI", holder.id_transaksi);
                holder.itemView.getContext().startActivity(detail);
            }
        });

        try {
            holder.id_transaksi = modelDikemas.getId_transaksi();
            holder.tanggalDikemas.setText("Tanggal Transaksi : " + modelDikemas.getTanggal_transaksi());
            holder.alamatPengiriman.setText(modelDikemas.getAlamat_pengiriman());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tanggalDikemas, alamatPengiriman;
        String url, id_transaksi;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            tanggalDikemas = itemView.findViewById(R.id.tvTanggalDikemas);
            alamatPengiriman = itemView.findViewById(R.id.tvAlamatPengirimanDikemas);
        }
    }
}

