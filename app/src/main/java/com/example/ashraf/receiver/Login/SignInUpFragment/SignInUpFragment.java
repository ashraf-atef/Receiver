package com.example.ashraf.receiver.Login.SignInUpFragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ashraf.receiver.Login.GoogleSigninFragment.GoobleSigninFragment;
import com.example.ashraf.receiver.Model.GoogleAccount;
import com.example.ashraf.receiver.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInUpFragment extends Fragment implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {


    ProgressDialog dialog;
    View rootView;
    SignInUpFragmentPresenter signInUpFragmentPresenter;
    Button signUpWithGoogleButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sign_in_up, container, false);
        signInUpFragmentPresenter = new SignInUpFragmentPresenterImpl(this);
        signInUpFragmentPresenter.initView();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.singUpWtithGoogleButton:
                signInUpFragmentPresenter.loginWithGoogleAccount();
                return;
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        signInUpFragmentPresenter.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onStop() {
        super.onStop();
        signInUpFragmentPresenter.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        signInUpFragmentPresenter.onDestroy();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        signInUpFragmentPresenter.onConnectionFailed( connectionResult);
    }

}
