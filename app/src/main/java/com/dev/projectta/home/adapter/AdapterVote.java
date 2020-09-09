package com.dev.projectta.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev.projectta.R;
import com.dev.projectta.home.interfaces.VoteInterface;
import com.dev.projectta.home.model.Candidate;
import com.dev.projectta.utils.apihelper.UtilsApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterVote extends RecyclerView.Adapter<AdapterVote.viewHolder> {


    private Context context;
    private List<Candidate.DataBean> dataBeanList;
    private VoteInterface voteInterface;


    private int lastSelectedPosition = -1;

    public AdapterVote(Context context, List<Candidate.DataBean> dataBeanList, VoteInterface voteInterface) {
        this.context = context;
        this.dataBeanList = dataBeanList;
        this.voteInterface = voteInterface;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_vote_list, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {
        holder.voteName.setText(dataBeanList.get(position).getNama());
        holder.voteNoBP.setText(dataBeanList.get(position).getNobp_candidate());
        holder.vote.setChecked(lastSelectedPosition == position);
        holder.vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
                voteInterface.onItemClick(Integer.parseInt(dataBeanList.get(position).getNobp_candidate()));
            }
        });

        Glide
                .with(context)
                .load(UtilsApi.BASE_URL1 + dataBeanList.get(position).getProfile_image())
                .centerCrop()
                .into(holder.voteImage);

        if (position == 0 && lastSelectedPosition == -1) {
            holder.vote.setChecked(true);
            voteInterface.onItemClick(position);
        }

    }

    @Override
    public int getItemCount() {
        return (dataBeanList != null) ? dataBeanList.size() : 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.voteImage)
        ImageView voteImage;
        @BindView(R.id.voteName)
        TextView voteName;
        @BindView(R.id.vote)
        CheckBox vote;
        @BindView(R.id.voteNoBP)
        TextView voteNoBP;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            if (lastSelectedPosition == -1) {
                voteInterface.onItemClick(Integer.parseInt(dataBeanList.get(0).getNobp_candidate()));
            }
        }
    }
}
