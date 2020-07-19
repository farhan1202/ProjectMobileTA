package com.dev.projectta.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.projectta.R;
import com.dev.projectta.home.model.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterResult extends RecyclerView.Adapter<AdapterResult.viewHolder> {

    private Context context;
    private List<Result.DataBean> dataBeans;

    public AdapterResult(Context context, List<Result.DataBean> dataBeans) {
        this.context = context;
        this.dataBeans = dataBeans;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_result_vote, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.jumlahSuara.setText(dataBeans.get(position).getJumlah_suara());
        holder.resultNoBP.setText(dataBeans.get(position).getNobp_candidate());
        holder.resultNama.setText(dataBeans.get(position).getNama());
        holder.resultJurusan.setText(dataBeans.get(position).getJurusan());
    }

    @Override
    public int getItemCount() {
        return (dataBeans != null) ? dataBeans.size() : 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.resultNoBP)
        TextView resultNoBP;
        @BindView(R.id.resultNama)
        TextView resultNama;
        @BindView(R.id.resultJurusan)
        TextView resultJurusan;
        @BindView(R.id.jumlah_suara)
        TextView jumlahSuara;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
