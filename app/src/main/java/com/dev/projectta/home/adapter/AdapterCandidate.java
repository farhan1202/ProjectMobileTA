package com.dev.projectta.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.projectta.R;
import com.dev.projectta.home.model.Candidate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterCandidate extends RecyclerView.Adapter<AdapterCandidate.viewHolder> {

    private Context context;
    private List<Candidate.DataBean> dataBeanList;

    public AdapterCandidate(Context context, List<Candidate.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_candidate, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.candidateName.setText(dataBeanList.get(position).getNama());
        holder.candidateBP.setText(dataBeanList.get(position).getNobp_candidate());
        holder.candidateJurusan.setText(dataBeanList.get(position).getJurusan());
        holder.candidateKeterangan.setText(dataBeanList.get(position).getKeterangan());
    }

    @Override
    public int getItemCount() {
        return (dataBeanList != null) ? dataBeanList.size() : 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.candidateName)
        TextView candidateName;
        @BindView(R.id.candidateBP)
        TextView candidateBP;
        @BindView(R.id.candidateJurusan)
        TextView candidateJurusan;
        @BindView(R.id.candidateKeterangan)
        TextView candidateKeterangan;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
