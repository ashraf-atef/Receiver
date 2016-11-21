package com.example.ashraf.receiver.Main;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.example.ashraf.receiver.MapFragment.MapsFragment;
import com.example.ashraf.receiver.Model.GoogleAccount;
import com.example.ashraf.receiver.Pubub.MyPubnub;
import com.example.ashraf.receiver.R;
import com.example.ashraf.receiver.RecycleFragment.RecycleviewFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static Intent intent;

    public static void set_intent(GoogleAccount googleAccount, Context context) {
        intent = new Intent(context, MainActivity.class);
        intent.putExtra("googleAccount", googleAccount);
    }

    public static Intent getmyIntent() {
        return intent;
    }


   public MyPubnub myPubnub;
    MapsFragment mapsFragment;
    RecycleviewFragment recycleviewFragment;

    TabLayout mTabLayout;
    ViewPager mPager;
    FloatingActionButton sendMessageFAB;
    EditText messageEdittext;
    CircleImageView img_userimage;
    TextView tv_useremail, tv_username;
    public NavigationView navigationView;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public DrawerLayout mDrawerLayout;
    Toolbar mToolbar;

    public MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityPresenter = new MainActivityPresenterImpl(this);
        mainActivityPresenter.initView(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainActivityPresenter.startPubnubConnection();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mainActivityPresenter.stopPubnubConnection();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMessageFloatActionButton:
                mainActivityPresenter.publishMessage(messageEdittext.getText().toString());
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mainActivityPresenter.requestPermission(requestCode, permissions, grantResults);

    }
}
