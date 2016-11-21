package com.example.ashraf.receiver.Main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;

import com.example.ashraf.receiver.Model.GoogleAccount;

/**
 * Created by ashraf on 11/21/2016.
 */

public interface MainActivityPresenter {
    void setGoogleAccunt();
    void initView(Bundle savedInstanceState);
    void setUser();
    void setupDrawerContent(NavigationView navigationView);
    void startPubnubConnection();
    void stopPubnubConnection();
    void setViewPager(ViewPager viewPager ,Bundle savedInstanceState ) ;
    void publishMessage(String content) ;
    void displayListionerResult(String text);
    void requestPermission(int requestCode, String[] permissions, int[] grantResults);

}
