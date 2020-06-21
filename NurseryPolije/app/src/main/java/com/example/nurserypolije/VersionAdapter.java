package com.example.nurserypolije;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VersionAdapter extends RecyclerView.Adapter<VersionAdapter.VersionVH> {

    List<Version> versionList;

    public VersionAdapter(List<Version> versionList) {
        this.versionList = versionList;
    }

    @NonNull
    @Override
    public VersionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new VersionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionVH holder, int position) {

        Version version = versionList.get(position);
        holder.pertanyaanTxt.setText(version.getPertanyaan());
        holder.jawabanTxt.setText(version.getJawaban());


        boolean isExpandable = versionList.get(position).isExpandable();
        holder.expandablelayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return versionList.size();
    }

    public class VersionVH extends RecyclerView.ViewHolder {

        TextView pertanyaanTxt, jawabanTxt;
        LinearLayout linearlayout;
        RelativeLayout expandablelayout;

        public VersionVH(@NonNull View itemView) {
            super(itemView);

            pertanyaanTxt = itemView.findViewById(R.id.pertanyaan);
            jawabanTxt = itemView.findViewById(R.id.jawaban);


            linearlayout = itemView.findViewById(R.id.linear_layout);
            expandablelayout = itemView.findViewById(R.id.expandable_layout);

            linearlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Version version = versionList.get(getAdapterPosition());
                    version.setExpandable(!version.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}
