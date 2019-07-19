package com.example.minymart.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minymart.Base.BaseActivity;
import com.example.minymart.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

public class DetailProductActivity extends BaseActivity {

    @BindView(R.id.txt_name_product)
    TextView mName;
    @BindView(R.id.img_detail)
    ImageView mImage;
    @BindView(R.id.txt_description_product)
    TextView mDescription;
    @BindView(R.id.quantity)
    EditText mQuantity;
    @BindView(R.id.increase)
    ImageButton mIncrease;
    @BindView(R.id.decrease)
    ImageButton mDecrease;
    String id, name, image, description;
    int mValue = 1;
    int mMin = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        attachView(this);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i=this.getIntent();
        id = i.getExtras().getString("ID");
        name = i.getExtras().getString("NAME");
        image = i.getExtras().getString("IMAGE");
        description = i.getExtras().getString("DESCRIPTION");
        mName.setText(name);
        mDescription.setText(description);
        mQuantity.setText(String.valueOf(mValue));
        Picasso.with(this)
                .load(image)
                .placeholder(R.drawable.empty_image)
                .into(mImage);
    }

    public void insertCart(){
        toCart();
    }

    public void onClickView(View view){
        switch (view.getId()){
            case R.id.arrow_back :
                onBackPressed();
                break;
            case R.id.btn_update_cart :
                insertCart();
                break;
            case R.id.shop :
                toCart();
                break;
            case R.id.increase :
                increaseInteger();
                break;
            case R.id.decrease :
                decreaseInteger();
                break;

        }
    }

    public void increaseInteger() {
        mValue = mValue + 1;
        mQuantity.setText(String.valueOf(mValue));
    }

    public void decreaseInteger() {
        if (mValue == 1){
            mDecrease.setEnabled(true);
            return;
        }
        mValue = mValue - 1;
        mQuantity.setText(String.valueOf(mValue));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
