package com.example.minymart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minymart.R;
import com.example.minymart.activity.ConfirmationActivity;
import com.example.minymart.model.Cart;

import java.util.List;


public class ConfirmationAdapter extends RecyclerView.Adapter<ConfirmationAdapter.MyViewHolder> {

    private Context mContext;
    private List<Cart> mPay;

    public ConfirmationAdapter(ConfirmationActivity confirmationActivity, List<Cart> pay) {
        mContext = confirmationActivity;
        mPay = pay;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_model_confirmation, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cart cart = mPay.get(position);
        holder.mName.setText(cart.getName());
        holder.mQty.setText(cart.getQty());
        holder.mPrice.setText(cart.getPrice());

    }

    @Override
    public int getItemCount() {
        return mPay.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mName, mPrice, mQty;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.txt_product_pay);
            mPrice = itemView.findViewById(R.id.txt_price);
            mQty = itemView.findViewById(R.id.txt_quantity);
        }
    }
}
