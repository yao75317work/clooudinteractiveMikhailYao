package com.kjyao.clooudinteractive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.kjyao.clooudinteractive.R;
import com.kjyao.clooudinteractive.bean.RespondData;
import com.kjyao.clooudinteractive.glide.GlideApp;

import java.util.ArrayList;

public class RespondsAdapter extends RecyclerView.Adapter<RespondsAdapter.RespondsViewHolder> {

    private final Context mContext;
    private ArrayList<RespondData> mDataList;
    private final ItemClickListener mItemClickListener;

    public RespondsAdapter(Context context, ItemClickListener itemClickListener) {
        mContext = context;
        mDataList = new ArrayList<>();
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RespondsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_respond, parent, false);
        return new RespondsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RespondsViewHolder holder, int position) {
        holder.onBind(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setDataList(ArrayList<RespondData> dataList) {
        if (dataList != null) {
            mDataList = dataList;
            notifyDataSetChanged();
        }
    }

    protected class RespondsViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageBackground;
        private final TextView textId;
        private final TextView textTitle;

        public RespondsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageBackground = itemView.findViewById(R.id.image_background);
            textId = itemView.findViewById(R.id.text_id);
            textTitle = itemView.findViewById(R.id.text_title);
            itemView.setOnClickListener(mOnClickListener);
        }

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onClick(v, mDataList.get(getAdapterPosition()));
            }
        };

        public void onBind(RespondData data) {
            textId.setText(data.getId());
            textTitle.setText(data.getTitle());

            if (mContext != null) {
                String url = data.getThumbnailUrl();
                GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
                        .addHeader("User-Agent",
                                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36")
                        .build());

                GlideApp.with(mContext).clear(imageBackground);
                GlideApp.with(mContext)
                        .load(glideUrl)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(imageBackground);
            }
        }
    }

    public interface ItemClickListener {
        void onClick(View v, RespondData data);
    }
}
