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

public class DikirimAdapter extends RecyclerView.Adapter<DikirimAdapter.HolderData> {
    private List<ModelDikirim> mItems;
    private Context context;
    private ArrayList<ModelDikirim> modelDikirim;

    DikirimAdapter(ArrayList<ModelDikirim> arrayList) {
        this.modelDikirim = arrayList;
    }

    public DikirimAdapter(Context context, List<ModelDikirim> mItems) {
        this.mItems = mItems;
        this.context = context;
    }

    @NonNull
    @Override
    public DikirimAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_dikirim, viewGroup, false);
        DikirimAdapter.HolderData holderData = new DikirimAdapter.HolderData((layout));
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull final DikirimAdapter.HolderData holder, int position) {
        ModelDikirim modelDikirim = mItems.get(position);
        //klik untuk Intent dan parsing data ke detail event
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent detail = new Intent(holder.itemView.getContext(), DetailTagihan.class);
//                detail.putExtra("ID_TRANSAKSI", holder.id_transaksi);
//                holder.itemView.getContext().startActivity(detail);
//            }
//        });

        try {
            holder.id_transaksi = modelDikirim.getId_transaksi();
            holder.tanggalDikirim.setText("Tanggal Dikirim : " + modelDikirim.getTanggal_transaksi());
            holder.alamatPengiriman.setText(modelDikirim.getAlamat_pengiriman());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tanggalDikirim, alamatPengiriman;
        String id_transaksi;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            tanggalDikirim = itemView.findViewById(R.id.tvTanggalDikirim);
            alamatPengiriman = itemView.findViewById(R.id.tvAlamatPengirimanDikirim);
        }
    }
}
