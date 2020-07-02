package com.example.nurserypolije;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TagihanAdapter extends RecyclerView.Adapter<TagihanAdapter.HolderData> {
    private List<ModelTagihan> mItems;
    private Context context;
    private ArrayList<ModelTagihan> modelTagihan;
    Integer miliSecond;

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
                Intent detail = new Intent(holder.itemView.getContext(), DetailTagihan.class);
                detail.putExtra("ID_TRANSAKSI", holder.id_transaksi);
                holder.itemView.getContext().startActivity(detail);
            }
        });

        try {

            miliSecond = modelTagihan.getTanggal_transaksi();
            holder.id_transaksi = modelTagihan.getId_transaksi();
            holder.tanggalTransaksi.setText("Tanggal Transaksi : " + getDate(miliSecond));
            holder.alamatPengiriman.setText(modelTagihan.getAlamat_pengiriman());
            holder.total.setText(modelTagihan.getTotal());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    private String getDate(long milliSeconds) {
//        // Create a DateFormatter object for displaying date in specified
//        // format.
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
//        // Create a calendar object that will convert the date and time value in
//        // milliseconds to date.
//        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
//        calendar.setTimeInMillis((int) milliSeconds * 1000L);
//        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", calendar).toString();
//        return formatter.format(calendar.getTime());
//    }
private String getDate(long time) {
    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    cal.setTimeInMillis(time * 1000);
    String date = DateFormat.format("dd-MM-yyyy", cal).toString();
    return date;
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
