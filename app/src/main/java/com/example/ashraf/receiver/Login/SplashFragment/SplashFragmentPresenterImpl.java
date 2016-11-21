package com.example.ashraf.receiver.Login.SplashFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;

import com.example.ashraf.receiver.Login.SignInUpFragment.SignInUpFragment;
import com.example.ashraf.receiver.Main.MainActivity;
import com.example.ashraf.receiver.Model.GoogleAccount;
import com.example.ashraf.receiver.R;
import com.example.ashraf.receiver.SharedPreference.MySharedPreference;

/**
 * Created by ashraf on 11/21/2016.
 */

public class SplashFragmentPresenterImpl implements SplashFragmentPresenter {
   SplashFragment splashFragment;
    public SplashFragmentPresenterImpl(SplashFragment splashFragment) {
        this.splashFragment=splashFragment;
    }

    @Override
    public void checkGPS() {
        LocationManager lm = (LocationManager) splashFragment.getContext().getSystemService(splashFragment.getContext().LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(splashFragment.getActivity(), R.style.myDialog));
            dialog.setMessage(splashFragment.getContext().getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(splashFragment.getContext().getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    splashFragment.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(splashFragment.getContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    splashFragment.getActivity().finish();
                }
            });
            dialog.show();
        } else {
           redirectAction();
        }

    }

    @Override
    public void redirectAction() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                    redirect();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }


    @Override
    public void redirect() {
        GoogleAccount googleAccount = MySharedPreference.getGoogleAccount(splashFragment.getContext());
        if (googleAccount == null) {
            android.support.v4.app.FragmentManager fm1 = splashFragment.getActivity().getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft1 = fm1.beginTransaction();
            ft1.replace(R.id.frame, new SignInUpFragment()).addToBackStack(null).commit();
        } else {
            MainActivity.set_intent(googleAccount, splashFragment.getContext());
            splashFragment.getActivity().startActivity
                    (MainActivity.getmyIntent().setFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)));
        }
    }
}
