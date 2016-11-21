package com.example.ashraf.receiver.Main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashraf.receiver.Login.LoginActivity.LoginActivity;
import com.example.ashraf.receiver.MapFragment.MapsFragment;
import com.example.ashraf.receiver.Model.ChatMessage;
import com.example.ashraf.receiver.Model.GoogleAccount;
import com.example.ashraf.receiver.Pubub.MyPubnub;
import com.example.ashraf.receiver.R;
import com.example.ashraf.receiver.RecycleFragment.RecycleviewFragment;
import com.example.ashraf.receiver.SharedPreference.MySharedPreference;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ashraf on 11/21/2016.
 */

public class MainActivityPresenterImpl implements MainActivityPresenter {
    MainActivity mainActivity;
    GoogleAccount googleAccount;

    public MainActivityPresenterImpl(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void setGoogleAccunt() {
        googleAccount = (GoogleAccount) mainActivity.getmyIntent().getSerializableExtra("googleAccount");
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setGoogleAccunt();
         mainActivity.mToolbar = (Toolbar)  mainActivity.findViewById(R.id.toolbar);
        mainActivity.setSupportActionBar(mainActivity.mToolbar);
        mainActivity.navigationView = (NavigationView)  mainActivity.findViewById(R.id.navigation_view);
        //        http://stackoverflow.com/questions/33194594/navigationview-get-find-header-layout
        View headerLayout =  mainActivity.navigationView.getHeaderView(0);
        mainActivity.tv_useremail = (TextView) headerLayout.findViewById(R.id.tv_useremail);
        mainActivity.tv_username = (TextView) headerLayout.findViewById(R.id.tv_username);
        mainActivity.img_userimage = (CircleImageView) headerLayout.findViewById(R.id.img_userimage);
        setUser();
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        if ( mainActivity.navigationView != null)  setupDrawerContent( mainActivity.navigationView);
        // Initializing Drawer Layout and ActionBarToggle
        mainActivity.mDrawerLayout = (DrawerLayout) mainActivity.findViewById(R.id.drawer_our_main);
        mainActivity.actionBarDrawerToggle = new ActionBarDrawerToggle(mainActivity, mainActivity.mDrawerLayout, mainActivity.mToolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };
        mainActivity.mDrawerLayout.setDrawerListener(mainActivity.actionBarDrawerToggle);
        mainActivity.actionBarDrawerToggle.syncState();
        mainActivity.mTabLayout = (TabLayout) mainActivity.findViewById(R.id.tabLayout);
        mainActivity.mPager = (ViewPager) mainActivity.findViewById(R.id.tab_viewpager);
        mainActivity.mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mainActivity.mTabLayout));
        mainActivity.sendMessageFAB = (FloatingActionButton) mainActivity.findViewById(R.id.sendMessageFloatActionButton);
        mainActivity.messageEdittext = (EditText) mainActivity.findViewById(R.id.messageEdittext);
        mainActivity.mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainActivity.mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        Log.d("SEQUENCE", "Actvivty created");
        mainActivity.myPubnub = new MyPubnub(mainActivity);
        Log.d("SEQUENCE", "Actvivty created pubnub created");
        mainActivity.sendMessageFAB.setOnClickListener(mainActivity);
        setViewPager(mainActivity.mPager, savedInstanceState);
        mainActivity.mTabLayout.setupWithViewPager(mainActivity.mPager);

        mainActivity.mToolbar.setLogo(R.drawable.human_foot_prints);
        mainActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public void setUser() {
        mainActivity.tv_useremail.setText(googleAccount.getEmial());
        mainActivity.tv_username.setText(googleAccount.getDisplayName());
        if (googleAccount.getPhotoUrl() != null)
            Picasso.with(mainActivity.getBaseContext())
                    .load(googleAccount.getPhotoUrl())
                    .placeholder(R.drawable.user)
                    .into(mainActivity.img_userimage);
    }

    @Override
    public void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                mainActivity.mDrawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.logout:

                        FirebaseAuth.getInstance().signOut();
                        MySharedPreference.deleteGoogleAccount(mainActivity.getBaseContext());
                        mainActivity.startActivity
                                (new Intent(mainActivity.getBaseContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        return true;


                    default:
                        Toast.makeText(mainActivity.getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });
    }

    @Override
    public void startPubnubConnection() {

        mainActivity.myPubnub.set_Config();
        mainActivity.myPubnub.subscribe();
        mainActivity.myPubnub.setListioner();
    }

    @Override
    public void stopPubnubConnection() {
        mainActivity.myPubnub.unsubscribe();
    }

    @Override
    public void setViewPager(ViewPager viewPager, Bundle savedInstanceState) {
        if (savedInstanceState==null) {
            mainActivity.mapsFragment = new MapsFragment();
            mainActivity.mapsFragment.setMyPubnub(mainActivity.myPubnub);
            mainActivity.recycleviewFragment = new RecycleviewFragment();
        }

        ViewPagerAdaptor adapter = new ViewPagerAdaptor(mainActivity.getSupportFragmentManager());
        adapter.addFrag(mainActivity.mapsFragment, "Maps");
        adapter.addFrag(mainActivity.recycleviewFragment, "Message");
        viewPager.setAdapter(adapter);

    }


    @Override
    public void publishMessage(String content ) {
        if (content.trim().length() > 0) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setPhotoUrl(googleAccount.getPhotoUrl());
            chatMessage.setPhotoType(null);
            chatMessage.setContent(content);
            chatMessage.setNameSender(googleAccount.getDisplayName());
            chatMessage.setDate(String.valueOf(new Date().getTime()));
            mainActivity.myPubnub.publishMessage(chatMessage);
            mainActivity.messageEdittext.setText("");
            Toast.makeText(mainActivity.getBaseContext(), "Message Pushed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void displayListionerResult(final String text) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (text != null && text.trim().length() > 0) {


                    String[] arr = text.replace("\"", "").replaceAll("\\[", "").replaceAll("\\]", "").split(",");
                    if (arr != null) {
                        if (arr[0].equals("0")) {
                            if (arr.length == 3 && mainActivity.mapsFragment.senderMarker != null) {
                                Toast.makeText(mainActivity.getBaseContext(), "Location Received", Toast.LENGTH_SHORT).show();
                                mainActivity.mapsFragment.senderMarker.setPosition(new LatLng(Double.parseDouble(arr[1]), Double.parseDouble(arr[2])));
                                Log.d("MARKER", String.valueOf(arr[1]) + "," + arr[2]);

                            }
                        } else if (arr[0].equals("1")) {
                            if (arr.length == 6) {
                                Toast.makeText(mainActivity.getBaseContext(), "Message Received", Toast.LENGTH_SHORT).show();
                                ChatMessage chatMessage = new ChatMessage();
                                chatMessage.setPhotoUrl(arr[1]);
                                chatMessage.setPhotoType(arr[2]);
                                chatMessage.setNameSender(arr[3]);
                                chatMessage.setDate(arr[4]);
                                chatMessage.setContent(arr[5]);
                                mainActivity.recycleviewFragment.recycleAdaptor.chatMessageList.add(chatMessage);
                                mainActivity.recycleviewFragment.recycleAdaptor.notifyDataSetChanged();

                                if (googleAccount.getDisplayName().equals(chatMessage.getNameSender())) {
                                    mainActivity.recycleviewFragment.recyclerView.scrollToPosition(
                                            mainActivity.recycleviewFragment.recycleAdaptor.getItemCount() - 1
                                    );
                                }
                                else
                                {

                                    if (mainActivity.recycleviewFragment.llm.findLastVisibleItemPosition()==
                                            mainActivity.recycleviewFragment.recycleAdaptor.getItemCount()-2)
                                    {
                                        mainActivity.recycleviewFragment.recyclerView.scrollToPosition(
                                                mainActivity.recycleviewFragment.recycleAdaptor.getItemCount() - 1
                                        );
                                    }
                                }

                            }
                        }
                    }
                } else {
                    Log.d("MESSAGE_NULL", null);
                }
            }
        });
    }

    @Override
    public void requestPermission(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mainActivity.mapsFragment.mapsFragmentPresenter.initComponants();

            } else { // if permission is not granted

                // decide what you want to do if you don't get permissions
            }
        }
    }
}
