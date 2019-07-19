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
import com.example.minymart.activity.CartActivity;
import com.example.minymart.activity.ConfirmationActivity;
import com.example.minymart.model.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context mContext;
    private List<Cart> mCart;

    public CartAdapter(CartActivity cartActivity, List<Cart> cart) {
        mContext = cartActivity;
        mCart = cart;
    }

    public CartAdapter(ConfirmationActivity confirmationActivity, List<Cart> cart) {
        mContext = confirmationActivity;
        mCart = cart;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_model_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cart cart = mCart.get(position);
        holder.mName.setText(cart.getName());
        holder.mQty.setText(cart.getQty());
        holder.mPrice.setText(cart.getTotal_price());
//        Picasso.with(mContext)
//                .load(cart.getImage())
//                .placeholder(R.drawable.empty_image)
//                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mCart.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mimage;
        TextView mName, mQty, mPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mimage = itemView.findViewById(R.id.img_content);
            mName = itemView.findViewById(R.id.txt_product_pay);
            mQty = itemView.findViewById(R.id.qty_total);
            mPrice = itemView.findViewById(R.id.txt_price);

        }
    }
}
