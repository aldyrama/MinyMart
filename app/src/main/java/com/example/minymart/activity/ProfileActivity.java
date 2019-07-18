package com.example.minymart.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.minymart.Base.BaseActivity;
import com.example.minymart.R;
import com.example.minymart.model.SharedPrefManager;

public class ProfileActivity extends BaseActivity {

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPrefManager = new SharedPrefManager(this);

    }

    public void onClickView(View view){
        switch (view.getId()){
            case R.id.button :
               logOut();
                break;
        }
    }
}
