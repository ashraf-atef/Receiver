package com.example.ashraf.receiver.Login.SplashFragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashraf.receiver.Login.SignInUpFragment.SignInUpFragment;
import com.example.ashraf.receiver.Main.MainActivity;
import com.example.ashraf.receiver.Model.GoogleAccount;
import com.example.ashraf.receiver.R;
import com.example.ashraf.receiver.SharedPreference.MySharedPreference;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {

    SplashFragmentPresenter splashFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        splashFragmentPresenter = new SplashFragmentPresenterImpl(this);
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        splashFragmentPresenter.checkGPS();
    }


}
