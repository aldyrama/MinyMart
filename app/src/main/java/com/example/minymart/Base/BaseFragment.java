package com.example.minymart.Base;

import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;

public class BaseFragment extends Fragment {

    public Fragment fragment;

    public void attachView(View view) {

        ButterKnife.bind(view);

    }
}
