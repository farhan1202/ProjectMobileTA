package com.dev.projectta.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev.projectta.R;
import com.dev.projectta.home.DetailCandidateActivity;
import com.dev.projectta.home.model.Candidate;
import com.dev.projectta.utils.apihelper.UtilsApi;

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
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        holder.candidateName.setText(dataBeanList.get(position).getNama());
        Glide
                .with(context)
                .load(UtilsApi.BASE_URL1 + dataBeanList.get(position).getProfile_image())
                .centerCrop()
                .into(holder.candidateImg);
        holder.cardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context.getApplicationContext(), DetailCandidateActivity.class);
                intent.putExtra("id_candidate", dataBeanList.get(position).getId() +"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataBeanList != null) ? dataBeanList.size() : 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.candidateImg)
        ImageView candidateImg;
        @BindView(R.id.candidateName)
        TextView candidateName;
        @BindView(R.id.cardButton)
        CardView cardButton;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
