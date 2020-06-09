package com.example.nurserypolije;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TagihanAdapter extends RecyclerView.Adapter<TagihanAdapter.HolderData> {
    private List<ModelTagihan> mItems;
    private Context context;
    private ArrayList<ModelTagihan> modelTagihan;

    TagihanAdapter(ArrayList<ModelTagihan> arrayList) {
        this.modelTagihan = arrayList;
    }

    public TagihanAdapter(Context context, List<ModelTagihan> mItems) {
        this.mItems = mItems;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_tagihan, viewGroup, false);
        TagihanAdapter.HolderData holderData = new TagihanAdapter.HolderData((layout));
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderData holder, int position) {
        ModelTagihan modelTagihan = mItems.get(position);
        //klik untuk Intent dan parsing data ke detail event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(holder.itemView.getContext(), LoginActivity.class);
                detail.putExtra("ID_TRANSAKSI", holder.id_transaksi);
                holder.itemView.getContext().startActivity(detail);
            }
        });

        try {
            holder.id_transaksi = modelTagihan.getId_transaksi();
            holder.tanggalTransaksi.setText("Tanggal Transaksi : " + modelTagihan.getTanggal_transaksi());
            holder.alamatPengiriman.setText(modelTagihan.getAlamat_pengiriman());
            holder.total.setText(modelTagihan.getTotal());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tanggalTransaksi, alamatPengiriman, total;
        String url, id_transaksi;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            tanggalTransaksi = itemView.findViewById(R.id.tvTanggalTransaksi);
            alamatPengiriman = itemView.findViewById(R.id.tvAlamatPengiriman);
            total = itemView.findViewById(R.id.tvTotal);
        }
    }
}
