package com.example.ashraf.receiver.Login.LoginActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ashraf.receiver.Login.SplashFragment.SplashFragment;
import com.example.ashraf.receiver.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frame, new SplashFragment());
        ft.commit();
    }
}
