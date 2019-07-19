package com.example.minymart.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minymart.R;
import com.example.minymart.activity.CategoryActivity;
import com.example.minymart.activity.HomeActivity;
import com.example.minymart.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Category> mCategory;
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private OnItemClickListener mListener;

    public CategoriesAdapter(HomeActivity homeActivity, List<Category> category) {
        mContext = homeActivity;
        mCategory = category;

    }

    public CategoriesAdapter(CategoryActivity categoryActivity, List<Category> category) {
        mContext = categoryActivity;
        mCategory = category;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_model_category, parent, false), i);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category = mCategory.get(position);

        holder.mTxt.setText(category.getName());
        Picasso.with(mContext)
                .load(category.getImage())
                .placeholder(R.drawable.empty_image)
                .into(holder.mImage);

    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;


    }

    public interface OnItemClickListener {

        void onItemClick(int position);

        void onShowItemClick(int position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
//        @BindView(R.id.img_category)
        ImageView mImage;
//        @BindView(R.id.txt_category)
        TextView mTxt;

        public MyViewHolder(@NonNull View itemView, int i) {
            super(itemView);
//            ButterKnife.bind(itemView);
            mTxt = itemView.findViewById(R.id.txt_category);
            mImage = itemView.findViewById(R.id.img_category);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {

                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {

                    mListener.onItemClick(position);

                }

            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {


            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }

}
