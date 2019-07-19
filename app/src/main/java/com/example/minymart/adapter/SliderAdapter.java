package com.example.minymart.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.minymart.R;
import com.example.minymart.model.Banner;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends
        SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context mContext;
    private int mCount;
    List<Banner> mBanner;

    public SliderAdapter(Context context, List<Banner> banner) {
        mContext = context;
        mBanner = banner;
    }


    public void setCount(int count) {
        this.mCount = count;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_model_slider, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        final Banner banner = mBanner.get(position);
        viewHolder.mTitle.setText(banner.getName());

        Glide.with(viewHolder.itemView)
                .load(banner.getImage())
                .fitCenter()
                .into(viewHolder.mImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "This is item in position " + position + banner.getUrl(), Toast.LENGTH_SHORT).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(banner.getUrl()));
                v.getContext().startActivity(browserIntent);
            }
        });

    }

    @Override
    public int getCount() {
        return mBanner.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView mImage, mGif;
        TextView mTitle;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.img_slider);
            mTitle = itemView.findViewById(R.id.txt_slider);
            this.itemView = itemView;
        }
    }


}
