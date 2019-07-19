package com.example.minymart.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minymart.R;
import com.example.minymart.activity.ProductsActivity;
import com.example.minymart.model.Products;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context mContext;
    private List<Products> mProduct;
    private OnItemClickListener mListener;

    public ProductAdapter(ProductsActivity productsActivity, List<Products> product) {
        mContext = productsActivity;
        mProduct = product;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_model_product, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Products products = mProduct.get(position);
        holder.mTitle.setText(products.getName());
        holder.mDescription.setText(products.getDescription());
        holder.mPrice.setText(products.getPrice());
        Picasso.with(mContext)
                .load(products.getImage())
                .placeholder(R.drawable.empty_image)
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mProduct.size();
    }

    public void setOnItemClickListener(ProductAdapter.OnItemClickListener listener) {

        mListener = listener;


    }

    public interface OnItemClickListener {

        void onItemClick(int position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mImage;
        TextView mTitle, mDescription, mPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.img_product);
            mTitle = itemView.findViewById(R.id.txt_product);
            mDescription = itemView.findViewById(R.id.txt_description);
            mPrice = itemView.findViewById(R.id.txt_vprice);

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
    }
}
